package com.mdm.equipmentservice.model.dto.base;

import com.mdm.equipmentservice.model.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Project}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto implements Serializable {
    private Long id;

    private String name;

    private String fundingSource;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}