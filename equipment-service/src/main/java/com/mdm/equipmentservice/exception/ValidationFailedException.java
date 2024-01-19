package com.mdm.equipmentservice.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationFailedException extends RuntimeException {
    List<String> errors;

    public ValidationFailedException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(message);
    }
}
