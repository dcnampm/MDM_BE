package com.mdm.equipmentservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdm.equipmentservice.exception.ResourceNotFoundException;
import com.mdm.equipmentservice.exception.ValidationFailedException;
import com.mdm.equipmentservice.model.entity.Equipment;
import com.mdm.equipmentservice.model.entity.EquipmentStatus;
import com.mdm.equipmentservice.model.entity.RepairStatus;
import com.mdm.equipmentservice.model.entity.RepairTicket;
import jakarta.annotation.Nullable;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ValidationUtil {
    private final Validator validator;

    private final MessageUtil messageUtil;




    @Autowired
    public ValidationUtil(Validator validator, MessageUtil messageUtil) {
        this.validator = validator;
        this.messageUtil = messageUtil;
    }

    /**
     * Validate list of dto in request body, such as Not Null, Not Empty ,...
     *
     * @param listOfDto dtoes to be validated
     * @param <T>       dto type
     *
     * @return list of error messages if any constraint was violated
     */
    public <T> List<String> validateConstraintsOnListOfDto(List<T> listOfDto) {
        List<String> constraintViolationsMessages = new ArrayList<>();
        Set<ConstraintViolation<Object>> tempConstraintViolation;
        T tempDto;
        for (int index = 0; index < listOfDto.size(); index++) {
            tempDto = listOfDto.get(index);
            tempConstraintViolation = validator.validate(tempDto);
            if (!tempConstraintViolation.isEmpty()) {
                constraintViolationsMessages.add(String.format(
                        "[%d]: %s",
                        index,
                        tempConstraintViolation.parallelStream().map(ConstraintViolation::getMessage).toList()
                ));
            }
        }
        return constraintViolationsMessages;
    }

    /**
     * Validate list of dto in request body, such as Not Null, Not Empty ,...
     * <br>
     * if validation failed, throw ValidationFailedException
     *
     * @param listOfDto     dtoes to be validated
     * @param <T>           dto type
     * @param customMessage custom message to throw if exception occur, if null, a default message will be returned
     *
     * @return list of error messages if any constraint was violated
     */
    public <T> void validateConstraintsOnListOfDto_andThrowExceptionIfHasError(List<T> listOfDto, @Nullable String customMessage) {
        List<String> errorMessages = validateConstraintsOnListOfDto(listOfDto);
        if (!errorMessages.isEmpty())
            throw new ValidationFailedException(
                    StringUtils.isNotBlank(customMessage) ? customMessage : messageUtil.getMessage("validationFailed"),
                    errorMessages
            );

    }

    /**
     * Validate if entity exist in db
     * <br>
     * throw ResourceNotFoundException if not found
     *
     * @param entityId
     * @param repository
     * @param <ID>
     */
    public <ID> void validateNotFound(
            ID entityId, JpaRepository<?, ID> repository, @Nullable String customMessage
    ) {
        boolean existById = repository.existsById(entityId);
        if (!existById) {
            throw new ResourceNotFoundException(StringUtils.isNotBlank(customMessage) ? customMessage : messageUtil.getMessage("resourceNotFound"));
        }
    }

    public void validateApprovalDateAfterCreatedDate(LocalDateTime approvalDate, LocalDateTime createdDate) {
        if (approvalDate.isBefore(createdDate)) {
            throw new ValidationFailedException(messageUtil.getMessage("approvalDateCannotBeforeCreatedDate"), null);
        }
    }

    public void validateEquipmentStatus(Equipment equipment, EquipmentStatus equipmentStatus, String messageKey) {
        if (!equipment.getStatus().equals(equipmentStatus)) {
            throw new ValidationFailedException(messageUtil.getMessage(messageKey), null);
        }
    }

    public void validateRepairStatus(RepairTicket repairTicket, RepairStatus repairStatus, String messageKey) {
        if (!repairTicket.getRepairStatus().equals(repairStatus)) {
            throw new ValidationFailedException(messageUtil.getMessage(messageKey), null);
        }
    }
}

