package com.mdm.equipmentservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LinkExpiredException extends RuntimeException {
    private String error;

    public LinkExpiredException(String error) {
        this.error = error;
    }
}
