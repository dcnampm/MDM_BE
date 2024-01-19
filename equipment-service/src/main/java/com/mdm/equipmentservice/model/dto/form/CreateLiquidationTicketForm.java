package com.mdm.equipmentservice.model.dto.form;

import com.mdm.equipmentservice.model.entity.LiquidationTicket;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link LiquidationTicket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLiquidationTicketForm implements Serializable {
    @NotNull(message = "{createdDateMustNotBeNull}")
    private LocalDateTime createdDate;

    private String creatorNote;

    @NotNull(message = "{liquidationDateMustNotBeNull}")
    private LocalDateTime liquidationDate;

    private String liquidationNote;

    @NotNull(message = "{priceMustNotBeNull}")
    private Double price;
}