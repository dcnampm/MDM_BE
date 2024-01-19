package com.mdm.equipmentservice.query.param;

import com.mdm.equipmentservice.model.entity.FileDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFileStoragesQueryParam {
    private Long associatedEntityId;

    private String associatedEntityType;

    private FileDescription fileDescription;

}
