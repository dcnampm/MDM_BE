package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.constant.ForgotPasswordConstant;
import com.mdm.equipmentservice.event.ForgotPasswordEvent;
import com.mdm.equipmentservice.exception.LinkExpiredException;
import com.mdm.equipmentservice.exception.ResourceNotFoundException;
//import com.mdm.equipmentservice.keycloak.repository.KeycloakRoleRepository;
//import com.mdm.equipmentservice.keycloak.repository.KeycloakUserRepository;
//import com.mdm.equipmentservice.keycloak.service.KeycloakRoleService;
//import com.mdm.equipmentservice.keycloak.service.KeycloakUserService;
import com.mdm.equipmentservice.mapper.ForgotPasswordMapper;
import com.mdm.equipmentservice.mapper.UserMapper;
import com.mdm.equipmentservice.model.dto.form.ChangePasswordForm;
import com.mdm.equipmentservice.model.dto.form.ConfirmResetPasswordForm;
import com.mdm.equipmentservice.model.dto.form.UpsertUserForm;
import com.mdm.equipmentservice.model.dto.fullinfo.UserDetailDto;
import com.mdm.equipmentservice.model.entity.*;
import com.mdm.equipmentservice.model.repository.DepartmentRepository;
import com.mdm.equipmentservice.model.repository.ForgotPasswordRepository;
import com.mdm.equipmentservice.model.repository.UserRepository;
import com.mdm.equipmentservice.query.param.GetUsersQueryParam;
import com.mdm.equipmentservice.service.FileStorageService;
import com.mdm.equipmentservice.service.UserService;
import com.mdm.equipmentservice.util.MessageUtil;
import com.mdm.equipmentservice.util.SecurityUtil;
import com.mdm.equipmentservice.util.UniqueValidationUtil;
import com.querydsl.core.types.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.mdm.equipmentservice.query.predicate.QueryUserPredicate.getAllUsersPredicate;

/**
 * This class contains methods that interact with the user data stored in the database and Keycloak. Due to the difficulty of preserving transactional integrity
 * when using Keycloak without implementing a user storage provider SPI, all transactions for user operations follow this procedure: First, the user data is
 * inserted into the database using transactions managed by Spring. If there is an error during this step, all transactions are rolled back. If the insertion is
 * successful, the user data is then committed to Keycloak. Please note that because of this process, user data may be stored temporarily in the database before
 * being committed to Keycloak. However, this is necessary to ensure the transactional integrity of the user operations.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final ForgotPasswordRepository forgotPasswordRepository;

    private final UserRepository userRepository;

//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ForgotPasswordMapper forgotPasswordMapper;

    private final UniqueValidationUtil uniqueValidationUtil;

    private final MessageUtil messageUtil;

//    private final Configuration configuration;
//
//    private final Keycloak keycloak;

    private final UserMapper userMapper;

//    private final RealmResource realmResource;

    private final DepartmentRepository departmentRepository;

//    private final KeycloakRoleRepository keycloakRoleRepository;
//
//    private final KeycloakUserRepository keycloakUserRepository;
//
//    private final KeycloakRoleService keycloakRoleService;
//
//    private final KeycloakUserService keycloakUserService;

    private final FileStorageService fileStorageService;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ForgotPasswordMapper forgotPasswordMapper,
                           UniqueValidationUtil uniqueValidationUtil, MessageUtil messageUtil,
                           UserMapper userMapper, DepartmentRepository departmentRepository,
                           FileStorageService fileStorageService, ApplicationEventPublisher applicationEventPublisher,
                           ForgotPasswordRepository forgotPasswordRepository) {
        this.userRepository = userRepository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.forgotPasswordMapper = forgotPasswordMapper;
        this.uniqueValidationUtil = uniqueValidationUtil;
        this.messageUtil = messageUtil;
//        this.configuration = configuration;
//        this.keycloak = keycloak;
        this.userMapper = userMapper;
        this.departmentRepository = departmentRepository;
//        this.keycloakRoleRepository = keycloakRoleRepository;
//        this.keycloakUserRepository = keycloakUserRepository;
//        this.keycloakRoleService = keycloakRoleService;
//        this.keycloakUserService = keycloakUserService;
        this.fileStorageService = fileStorageService;
        this.applicationEventPublisher = applicationEventPublisher;
//        this.realmResource = this.keycloak.realm(this.configuration.getRealm());
        this.forgotPasswordRepository = forgotPasswordRepository;
    }

    @Override
    public UserDetailDto getUserById(Long userIdToBeRetrieved) {
        User user = userRepository.findById(userIdToBeRetrieved).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("userNotFound")));
        Long imageId = getUserAvatarId(user.getId());
        return userMapper.toUserDetailDto(user, imageId);
    }

    @Override
    public Page<UserDetailDto> getUsers(GetUsersQueryParam getUsersQueryParam, Pageable pageRequest) {
        Predicate getUsersPredicate = getAllUsersPredicate(getUsersQueryParam);
        return userRepository.findAll(getUsersPredicate, pageRequest).map(user -> {
            Long imageId = getUserAvatarId(user.getId());
            return userMapper.toUserDetailDto(user, imageId);
        });
    }

    @Override
    public UserDetailDto createUser(UpsertUserForm upsertUserForm, MultipartFile image) {
        User user = userMapper.toEntity(upsertUserForm);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(user, userRepository, messageUtil.getMessage("userUniquenessValidationFailed"));
        user = userRepository.save(user);
        Long imageId = null;
        if (image != null && !image.isEmpty()) {
            List<FileStorage> fileStorages = fileStorageService.uploadMultipleFiles(User.class.getSimpleName(), user.getId(), FileDescription.IMAGE, image);
            imageId = fileStorages.get(0).getId();
        }
//        keycloakUserService.createUser(user, upsertUserForm.getPassword());
        return userMapper.toUserDetailDto(user, imageId);
    }

    /**
     * Update user. If the user is trying to edit username and the realm does not allow editing username, throw an exception.
     * <br>
     * Before assigning the new role to the user, the old role associated with this user will be removed.
     * <br>
     * Also, we have to update the user information in Keycloak, including the user's role and password. Because we are using keycloak as an authorization
     * server, so the user's role and password must be updated as well.
     *
     * @param userIdToBeUpdated the id of the user to be updated
     * @param upsertUserForm    the dto containing the new user data
     * @param image             the image file
     * @return the updated user
     */
    @Override
    public UserDetailDto updateUser(Long userIdToBeUpdated, UpsertUserForm upsertUserForm, MultipartFile image) {
        User userToBeUpdated =
                userRepository.findById(userIdToBeUpdated).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("userNotFoundInDatabase")));
        checkIfIsTryingToUpdateUsername(upsertUserForm, userToBeUpdated);
        //save old roleName before mapping dto to entity, then this roleName will be used to remove the old role associated with this user
        userToBeUpdated = userMapper.partialUpdate(upsertUserForm, userToBeUpdated);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(userToBeUpdated, userRepository, messageUtil.getMessage("userUniquenessValidationFailed"));
        userRepository.save(userToBeUpdated);
//        keycloakUserService.updatePassword(userToBeUpdated.getUsername(), upsertUserForm.getPassword());
        Long imageId = null;
        if (image != null && !image.isEmpty()) {
            fileStorageService.deleteAllFilesOfAnEntity(User.class.getSimpleName(), userToBeUpdated.getId(), FileDescription.IMAGE);
            List<FileStorage> fileStorages =
                    fileStorageService.uploadMultipleFiles(User.class.getSimpleName(), userToBeUpdated.getId(), FileDescription.IMAGE, image);
            imageId = fileStorages.get(0).getId();
        }
        return userMapper.toUserDetailDto(userToBeUpdated, imageId);
    }

    /**
     * Check if the user is trying to edit username and the keycloak does not allow editing username, throw an exception.
     * <br>
     *
     * @param upsertUserForm
     * @param userToBeUpdated
     */
    private void checkIfIsTryingToUpdateUsername(UpsertUserForm upsertUserForm, User userToBeUpdated) {
        if (isTryingToEditUsername(upsertUserForm, userToBeUpdated)) {
            throw new UnsupportedOperationException(messageUtil.getMessage("editingUsernameIsNotAllowed"));
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User userToBeDeleted = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("userNotFound")));
        String username = userToBeDeleted.getUsername();
        userRepository.deleteById(userId);
//        keycloakUserRepository.deleteByUsername(username);
    }

    @Override
    public UserDetailDto getUserByUsername(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("userNotFound")));
        Long imageId = getUserAvatarId(user.getId());
        return userMapper.toUserDetailDto(user, imageId);
    }

    /**
     * Update current logged-in user detail (update profile). If the user is trying to edit username and the realm does not allow editing username, throw an
     * exception. This is a special case of {@link #updateUser(Long, UpsertUserForm, MultipartFile)} method.
     * <br>
     * <p>
     * Also, we have to update the user information in Keycloak, including the user's role and password. Because we are using keycloak as an authorization
     * server, so the user's role and password must be updated as well. In this case, the user cannot change his/her role, so we don't need to update the user's
     * role in Keycloak.
     *
     * @param upsertUserForm
     * @param image
     * @return
     */
    @Override
    public UserDetailDto updateCurrentLoggedInUserDetail(UpsertUserForm upsertUserForm, MultipartFile image) {
        User currentLoggedInUser = SecurityUtil.getCurrentLoggedInUser(userRepository);
        checkIfIsTryingToUpdateUsername(upsertUserForm, currentLoggedInUser);
        currentLoggedInUser = userMapper.updateProfile(upsertUserForm, currentLoggedInUser);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                currentLoggedInUser,
                userRepository,
                messageUtil.getMessage("userUniquenessValidationFailed")
        );
        //if user update new image, delete old image and upload new image
        Long newImageId = null;
        if (image != null && !image.isEmpty()) {
            fileStorageService.deleteAllFilesOfAnEntity(User.class.getSimpleName(), currentLoggedInUser.getId(), FileDescription.IMAGE);
            List<FileStorage> fileStorages =
                    fileStorageService.uploadMultipleFiles(User.class.getSimpleName(), currentLoggedInUser.getId(), FileDescription.IMAGE, image);
            newImageId = fileStorages.get(0).getId();
        }
        userRepository.save(currentLoggedInUser);
        return userMapper.toUserDetailDto(currentLoggedInUser, newImageId);
    }


    /**
     * Get current logged-in user detail. By using this method, the other user cannot get the detail of another user.
     *
     * @return the current logged-in user detail
     */
    @Override
    public UserDetailDto getCurrentLoggedInUserDetail() {
        User currentLoggedInUser = SecurityUtil.getCurrentLoggedInUser(userRepository);
        Long imageId = getUserAvatarId(currentLoggedInUser.getId());
        return userMapper.toUserDetailDto(currentLoggedInUser, imageId);
    }

    @Override
    public List<UserDetailDto> getAllUserInRoles(List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            return new ArrayList<>();
        }
        List<User> users = userRepository.findByRoleNameInIgnoreCase(roles);
        return userMapper.toUserDetailDtoList(users);
    }

    /**
     * get user avatar id
     *
     * @return the id of {@link FileStorage} entity
     */
    private Long getUserAvatarId(Long userId) {
        List<FileStorage> allFilesOfAnEntity = fileStorageService.getAllFilesOfAnEntity(User.class.getSimpleName(), userId, FileDescription.IMAGE);
        return allFilesOfAnEntity == null || allFilesOfAnEntity.isEmpty() ? null : allFilesOfAnEntity.get(0).getId();
    }

    /**
     * Logout current logged-in user. Because keycloak does not support logout via rest api (or I don't know how to logout using rest api), so we have to use
     * keycloak java api to logout. The client will call this method to log out the current logged-in user.
     */
    @Override
    public void logout() {
        String username = SecurityUtil.getCurrentLoggedInUser(userRepository).getUsername();
//        keycloakUserRepository.getResourceByUsername(username).logout();
        SecurityContextHolder.clearContext();
    }

    /**
     * Change password of current logged-in user. We will compare the old password with the current password of the current logged-in user. The current password
     * is stored in db. If the old password is correct, we will update the password in db and keycloak.
     *
     * @param changePasswordForm the dto containing old password, new password and confirm password
     * @return the user detail dto
     */
    @Override
    public UserDetailDto changePassword(ChangePasswordForm changePasswordForm) {
        checkIfConfirmationPasswordIsMatch(changePasswordForm.getNewPassword(), changePasswordForm.getConfirmPassword());
        User currentLoggedInUser = SecurityUtil.getCurrentLoggedInUser(userRepository);
//        checkIfOldPasswordIsCorrect(currentLoggedInUser.getPassword(), changePasswordForm.getOldPassword());
//        currentLoggedInUser.setPassword(bCryptPasswordEncoder.encode(changePasswordForm.getNewPassword()));
        userRepository.save(currentLoggedInUser);
//        keycloakUserRepository.updatePassword(changePasswordForm.getNewPassword(), currentLoggedInUser.getUsername());
        return userMapper.toUserDetailDto(currentLoggedInUser, null);
    }

    private void checkIfConfirmationPasswordIsMatch(String newPassword, String confirmPassword) {
        if (!StringUtils.equals(newPassword, confirmPassword)) {
            throw new IllegalArgumentException(messageUtil.getMessage("passwordConfirmationDoesNotMatch"));
        }
    }

    /**
     * send a new password to user email. User will log in with that password.
     * <br>
     *
     * @param email              email address of the user
     * @param httpServletRequest
     */
    @Override
    public void resetPassword(String email, HttpServletRequest httpServletRequest) {
        User user = userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("emailNotFound")));
        ForgotPassword forgotPassword = forgotPasswordMapper.toEntity(user);
        forgotPasswordRepository.save(forgotPassword);
        ForgotPasswordEvent forgotPasswordEvent = new ForgotPasswordEvent(forgotPassword, email, httpServletRequest.getHeader("Origin"));
        applicationEventPublisher.publishEvent(forgotPasswordEvent);
    }

    @Override
    @Transactional
    public void confirmResetPassword(ConfirmResetPasswordForm confirmResetPasswordForm) {
        checkIfConfirmationPasswordIsMatch(confirmResetPasswordForm.getNewPassword(), confirmResetPasswordForm.getConfirmPassword());
        ForgotPassword forgotPassword = forgotPasswordRepository.findByUuid(confirmResetPasswordForm.getUuid())
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("invalidLink")));
        checkIfForgotPasswordIsExpired(forgotPassword);
        User user = forgotPassword.getUser();
//        user.setPassword(bCryptPasswordEncoder.encode(confirmResetPasswordForm.getNewPassword()));
        userRepository.save(user);
//        keycloakUserRepository.updatePassword(confirmResetPasswordForm.getConfirmPassword(), user.getUsername());
        forgotPasswordRepository.delete(forgotPassword);
    }

    private void checkIfForgotPasswordIsExpired(ForgotPassword forgotPassword) {
        if (forgotPassword.getCreatedAt() > System.currentTimeMillis() + ForgotPasswordConstant.LINK_EXPIRED_AFTER_MILLISECONDS) {
            forgotPasswordRepository.delete(forgotPassword);
            throw new LinkExpiredException(messageUtil.getMessage("theLinkToResetPasswordIsExpired"));
        }
    }

//    /**
//     * Check if the old password is correct.
//     *
//     * @param currentHashedPassword the current hashed password of the user
//     * @param passwordToBeChecked   the password to be checked
//     */
//    private void checkIfOldPasswordIsCorrect(String currentHashedPassword, String passwordToBeChecked) {
//        if (!bCryptPasswordEncoder.matches(passwordToBeChecked, currentHashedPassword)) {
//            throw new BadCredentialsException(messageUtil.getMessage("oldPasswordIsIncorrect"));
//        }
//    }

    private boolean isTryingToEditUsername(UpsertUserForm requestBodyUserDto, User userToBeUpdated) {
        return !requestBodyUserDto.getUsername().equals(userToBeUpdated.getUsername());
    }

}
