package com.mdm.equipmentservice.exception;

import lombok.Getter;

@Getter
public class ResourceAlreadyExistException extends RuntimeException {
    public ResourceAlreadyExistException(String message) {
        super(message);
    }

}
