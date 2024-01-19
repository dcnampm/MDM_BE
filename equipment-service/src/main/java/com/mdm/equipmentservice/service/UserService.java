package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.form.ChangePasswordForm;
import com.mdm.equipmentservice.model.dto.form.ConfirmResetPasswordForm;
import com.mdm.equipmentservice.model.dto.form.UpsertUserForm;
import com.mdm.equipmentservice.model.dto.fullinfo.UserDetailDto;
import com.mdm.equipmentservice.query.param.GetUsersQueryParam;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface UserService {
    @PreAuthorize("hasAnyAuthority(\"USER.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    UserDetailDto getUserById(Long userId);

    @PreAuthorize("hasAuthority(\"USER.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    Page<UserDetailDto> getUsers(GetUsersQueryParam getUsersQueryParam, Pageable pageRequest);

//    @PreAuthorize("hasAuthority(\"USER.CREATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    UserDetailDto createUser(UpsertUserForm upsertUserForm, MultipartFile image);

    @PreAuthorize("hasAnyAuthority(\"USER.UPDATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    UserDetailDto updateUser(Long userId, UpsertUserForm upsertUserForm, MultipartFile image) ;

    @PreAuthorize("hasAuthority(\"USER.DELETE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void deleteUser(Long userId);

    @PreAuthorize("hasAnyAuthority(\"USER.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    UserDetailDto getUserByUsername(String username);

    UserDetailDto updateCurrentLoggedInUserDetail(UpsertUserForm userDetailDto, MultipartFile image);

    UserDetailDto getCurrentLoggedInUserDetail();

    List<UserDetailDto> getAllUserInRoles(List<String> roles);

    void logout();

    UserDetailDto changePassword(ChangePasswordForm changePasswordForm);

    void resetPassword(String email, HttpServletRequest httpServletRequest);

    void confirmResetPassword(ConfirmResetPasswordForm confirmResetPasswordForm);

/*


    @PreAuthorize("hasAuthority(\"USER.DELETE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void deleteUser(Long userId);
    List<User> getAllUserAssignedToRole(String roleName);*/
}
