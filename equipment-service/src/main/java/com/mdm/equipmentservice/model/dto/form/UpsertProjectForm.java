package com.mdm.equipmentservice.model.dto.form;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.Project}
 */
public record UpsertProjectForm(String name, String fundingSource, LocalDateTime startDate, LocalDateTime endDate) implements Serializable {

}