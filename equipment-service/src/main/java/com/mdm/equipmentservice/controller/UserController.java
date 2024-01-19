package com.mdm.equipmentservice.controller;

import com.mdm.equipmentservice.exception.ResourceNotFoundException;
import com.mdm.equipmentservice.model.dto.base.DepartmentDto;
import com.mdm.equipmentservice.model.dto.form.ChangePasswordForm;
import com.mdm.equipmentservice.model.dto.form.ConfirmResetPasswordForm;
import com.mdm.equipmentservice.model.dto.form.UpsertUserForm;
import com.mdm.equipmentservice.model.dto.fullinfo.UserDetailDto;
import com.mdm.equipmentservice.model.entity.Department;
import com.mdm.equipmentservice.model.entity.User;
import com.mdm.equipmentservice.query.param.GetUsersQueryParam;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.UserService;
import com.mdm.equipmentservice.util.MessageUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(value = "api/v1/users")
public class UserController {
    private final UserService userService;

    private final PagedResourcesAssembler<UserDetailDto> pagedResourcesAssembler;

    private final MessageUtil messageUtil;

    @Autowired
    public UserController(
            UserService userService, PagedResourcesAssembler<UserDetailDto> pagedResourcesAssembler,
            MessageUtil messageUtil
    ) {
        this.userService = userService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.messageUtil = messageUtil;
    }

    /**
     * Get user by user id. Throw {@link ResourceNotFoundException} if not found
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<GenericResponse<UserDetailDto>> getUser(@PathVariable("id") @NotBlank Long userId) {
        UserDetailDto user = userService.getUserById(userId);
        return ResponseEntity.ok(new GenericResponse<>(user));
    }

    @GetMapping(value = "/by-username/{username}")
    public ResponseEntity<GenericResponse<UserDetailDto>> getUserByUsername(
            @PathVariable("username") @NotBlank String username
    ) throws AccessDeniedException {
        UserDetailDto user = userService.getUserByUsername(username);
        return ResponseEntity.ok(new GenericResponse<>(user));
    }

    @GetMapping
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<UserDetailDto>>>> getUsers(
            GetUsersQueryParam getUsersQueryParam, Pageable pageRequest
    ) {
        Page<UserDetailDto> userPage = userService.getUsers(getUsersQueryParam, pageRequest);
        PagedModel<EntityModel<UserDetailDto>> entityModels = pagedResourcesAssembler.toModel(userPage);
        entityModels.forEach(userDtoWithDepartmentEntityModel -> userDtoWithDepartmentEntityModel.add(linkTo(methodOn(UserController.class).getUser(Objects.requireNonNull(
                userDtoWithDepartmentEntityModel.getContent()).getId())).withSelfRel()));
        return ResponseEntity.ok(new GenericResponse<>(entityModels));
    }
    @GetMapping("/current-user")
    public ResponseEntity<GenericResponse<UserDetailDto>> getCurrentLoggedInUserDetail() {
        UserDetailDto currentLoggedInUserDetail = userService.getCurrentLoggedInUserDetail();
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(currentLoggedInUserDetail));
    }

    @PostMapping(value = "/current-user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse<UserDetailDto>> updateCurrentLoggedInUserDetail(
            @RequestPart UpsertUserForm user, @RequestPart(required = false) MultipartFile image
    ) {
        UserDetailDto currentLoggedInUserDetail = userService.updateCurrentLoggedInUserDetail(user, image);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(currentLoggedInUserDetail));
    }

    @PostMapping(value = "/current-user/change-password")
    public ResponseEntity<GenericResponse<UserDetailDto>> changePassword(
            @RequestBody @Valid ChangePasswordForm changePasswordForm
    ) {
        UserDetailDto currentLoggedInUserDetail = userService.changePassword(changePasswordForm);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(currentLoggedInUserDetail));
    }


    @PostMapping(value ="/create-user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse<UserDetailDto>> createUser(
            @RequestPart @Valid UpsertUserForm user, @RequestPart(required = false) MultipartFile image
    ) {
        UserDetailDto createdUser = userService.createUser(user, image);
        return new ResponseEntity<>(new GenericResponse<>(createdUser, 201), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse<UserDetailDto>> updateUser(
            @PathVariable("id") @NotBlank Long userId, @RequestPart(name = "user") @Valid UpsertUserForm upsertUserForm,
            @RequestPart(required = false) MultipartFile image
    ) throws AccessDeniedException {
        UserDetailDto updatedUser = userService.updateUser(userId, upsertUserForm, image);
        return new ResponseEntity<>(new GenericResponse<>(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<?>> deleteUser(@PathVariable("id") @NotBlank Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(new GenericResponse<>(null, 200), HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<GenericResponse<?>> logout() {
        userService.logout();
        return new ResponseEntity<>(new GenericResponse<>(null, 204), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<GenericResponse<?>> resetPassword(@RequestBody String email, HttpServletRequest httpServletRequest) {
        userService.resetPassword(email, httpServletRequest);
        return new ResponseEntity<>(new GenericResponse<>(null, 200, messageUtil.getMessage("aNewLinkWasSentToYourEmail")), HttpStatus.OK);
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<GenericResponse<?>> confirmResetPassword(@RequestBody @Valid ConfirmResetPasswordForm confirmResetPasswordForm) {
        userService.confirmResetPassword(confirmResetPasswordForm);
        return new ResponseEntity<>(new GenericResponse<>(null, 200, messageUtil.getMessage("passwordReset")), HttpStatus.OK);
    }
}