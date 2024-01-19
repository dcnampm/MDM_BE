package com.mdm.equipmentservice.model.dto.base;

import com.mdm.equipmentservice.model.entity.FileDescription;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.FileStorage}
 */
@Value
public class FileStorageDto implements Serializable {
    Long id;

    String name;

    String extension;

    String contentType;

    String associatedEntityType;

    Long associatedEntityId;

    FileDescription description;
}