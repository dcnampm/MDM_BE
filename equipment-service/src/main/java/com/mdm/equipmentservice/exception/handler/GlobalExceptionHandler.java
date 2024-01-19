package com.mdm.equipmentservice.exception.handler;

import com.mdm.equipmentservice.exception.LinkExpiredException;
import com.mdm.equipmentservice.exception.ResourceAlreadyExistException;
import com.mdm.equipmentservice.exception.ResourceNotFoundException;
import com.mdm.equipmentservice.exception.ValidationFailedException;
import com.mdm.equipmentservice.response.ErrorResponse;
import com.mdm.equipmentservice.util.MessageUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageUtil messageUtil;

    @Autowired
    public GlobalExceptionHandler(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
    ) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getBindingResult()
                        .getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()),
                messageUtil.getMessage("validationFailed"),
                status.value(),
                servletWebRequest.getRequest().getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getCode()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException ex, WebRequest request
    ) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        ErrorResponse errorResponse = new ErrorResponse(
                Collections.singletonList(ex.getMessage()),
                messageUtil.getMessage("equipmentUniqueValidationFailed"),
                409,
                servletWebRequest.getRequest().getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getCode()));
    }

    @ExceptionHandler(ValidationFailedException.class)
    public ResponseEntity<Object> handleConstraintViolationException(
            ValidationFailedException ex, WebRequest request
    ) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrors(), ex.getMessage(), 409, servletWebRequest.getRequest().getRequestURI());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getCode()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, WebRequest request
    ) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        ErrorResponse errorResponse = new ErrorResponse(
                Collections.singletonList("Something went wrong"),
                null,
                500,
                servletWebRequest.getRequest().getRequestURI()
        );
        ex.printStackTrace();
        if (isUniqueConstraintViolation(ex)) {
            errorResponse.setErrors(Collections.singletonList(ex.getMessage()));
            errorResponse.setMessage(messageUtil.getMessage("attributeAlreadyExist", ((ConstraintViolationException) ex.getCause()).getConstraintName()));
            errorResponse.setCode(409);
            errorResponse.setPath(servletWebRequest.getRequest().getRequestURI());
        }
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getCode()));
    }

    private boolean isUniqueConstraintViolation(DataIntegrityViolationException ex) {
        if (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause().getMessage().contains("Duplicate entry")) {
            return true;
        }
        return false;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request
    ) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        ErrorResponse errorResponse = new ErrorResponse(
                Collections.singletonList(ex.getLocalizedMessage()),
                messageUtil.getMessage("resourceNotFound"),
                404,
                servletWebRequest.getRequest().getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getCode()));
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<Object> handleResourceAlreadyExistException(
            ResourceAlreadyExistException ex, WebRequest request
    ) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        ErrorResponse errorResponse = new ErrorResponse(
                Collections.singletonList(ex.getLocalizedMessage()),
                messageUtil.getMessage("resourceAlreadyExist"),
                409,
                servletWebRequest.getRequest().getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getCode()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(
            AccessDeniedException ex, WebRequest request
    ) {
        ex.printStackTrace();
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        ErrorResponse errorResponse = new ErrorResponse(
                Collections.singletonList(ex.getLocalizedMessage()),
                messageUtil.getMessage("youDoNotHavePermissionToDoThat"),
                403,
                servletWebRequest.getRequest().getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherException(
            Exception ex, WebRequest request
    ) {
        ex.printStackTrace();
        ErrorResponse errorResponse;
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        if (ex instanceof LinkExpiredException) {
            errorResponse = new ErrorResponse(
                    Collections.singletonList(((LinkExpiredException) ex).getError()),
                    null,
                    403,
                    servletWebRequest.getRequest().getRequestURI()
            );
        } else {

            errorResponse = new ErrorResponse(
                    Collections.singletonList(ex.getLocalizedMessage()),
                    messageUtil.getMessage("somethingWentWrong"),
                    500,
                    servletWebRequest.getRequest().getRequestURI()
            );
        }
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getCode()));
    }


    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<Object> handleUnsupportedOperationException(
            Exception ex, WebRequest request
    ) {
        ex.printStackTrace();
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        ErrorResponse errorResponse = new ErrorResponse(
                Collections.singletonList(ex.getLocalizedMessage()),
                messageUtil.getMessage("unsupportedOperation"),
                403,
                servletWebRequest.getRequest().getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getCode()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(
            BadCredentialsException ex, WebRequest request
    ) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        ErrorResponse errorResponse = new ErrorResponse(
                Collections.singletonList(ex.getLocalizedMessage()),
                messageUtil.getMessage("badCredentials"),
                401,
                servletWebRequest.getRequest().getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getCode()));
    }
}
