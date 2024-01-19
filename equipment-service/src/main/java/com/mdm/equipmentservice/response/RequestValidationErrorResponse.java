/*
package com.mdm.equipmentservice.response;

import lombok.Getter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RequestValidationErrorResponse extends GenericResponse {

    private final List<String> errors;


    public RequestValidationErrorResponse(String message, Integer statusCode, List<ObjectError> errors) {
        super(message, statusCode);
        this.errors = errors.stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());
    }

}
*/
