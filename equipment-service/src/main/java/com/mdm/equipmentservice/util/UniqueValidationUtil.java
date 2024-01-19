package com.mdm.equipmentservice.util;


import com.mdm.equipmentservice.exception.ValidationFailedException;
import jakarta.annotation.Nullable;
import jakarta.persistence.Table;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class UniqueValidationUtil {
    private final MessageUtil messageUtil;

    @Autowired
    public UniqueValidationUtil(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }


    /**
     * Validate unique constraint and throw exception if it has any error
     *
     * @param entityToBeValidated entity to be validated
     * @param entityRepository    inject the repository bean of this ENTITY class: ex: EquipmentRepository bean
     * @param customMessage       if custom message is blank, the exception will return the default message
     * @param <ENTITY>            entity type, ex: Equipment
     */
    public <ENTITY> void validateUnique_throwExceptionIfHasError(
            ENTITY entityToBeValidated, JpaRepository<ENTITY, ?> entityRepository, @Nullable String customMessage
    ) {
        List<String> errors = validateUnique(entityToBeValidated, entityRepository);
        if (!errors.isEmpty()) {
            throw new ValidationFailedException(StringUtils.isNotBlank(customMessage) ? customMessage : messageUtil.getMessage("uniquenessValidationFailed"),
                    errors
            );
        }
    }

    /**
     * Validate unique constraint on an entity. And return a list of constraint violated message.
     * <br>
     * The entity must be annotated with @Entity and @Table.
     * <br>
     * There are 2 cases: create and update entity
     * <pre>
     *     For example:
     * &#064;Entity
     * &#064;Table(name = "equipments", uniqueConstraints = {
     * &#064;UniqueConstraint(columnNames = {"serial"}, name = "serial"),
     * &#064;UniqueConstraint(columnNames = {"code"}, name = "code"),
     * &#064;UniqueConstraint(columnNames = {"hashCode"}, name = "hash_code")
     * </pre>
     * }) By using query by example, we can validate the uniqueness of the entity without having to declare the existBy... or findBy... method
     * <br>
     *
     * @param entityToBeValidated Example: Equipment
     * @param entityRepository    Inject the entity's repository bean: Ex: EquipmentRepository
     * @param <ENTITY>            Entity class
     *
     * @return List of message
     */
    public <ENTITY> List<String> validateUnique(
            ENTITY entityToBeValidated, JpaRepository<ENTITY, ?> entityRepository
    ) {
        List<String> constraintViolationMessages = new ArrayList<>();
        List<String> listOfUniqueFieldNames = getUniqueFieldNamesFromEntity(entityToBeValidated);
        //listOfUniqueFieldNames = [serial, code, hashCode]
        listOfUniqueFieldNames.parallelStream().forEach(fieldName -> {
            //assume that fieldName = hashCode
            try {
                //hashCode -> HashCode
                String fieldNameUpperCaseFirstLetter = CommonUtil.upperCaseFirstLetter(fieldName);
                //identical with valueOfFieldOfEntity = entityToBeValidated.getHashCode()
                String valueOfFieldOfEntity = Objects.toString(entityToBeValidated.getClass()
                        .getMethod("get" + fieldNameUpperCaseFirstLetter)
                        .invoke(entityToBeValidated));
                //value can be null, if null, skip
                if (StringUtils.isBlank(valueOfFieldOfEntity)) {
                    return;
                }
                List<ENTITY> result = queryEntityInDbByExample((Class<ENTITY>) entityToBeValidated.getClass(),
                        entityRepository,
                        fieldName,
                        valueOfFieldOfEntity
                );
                //empty result => unique
                if (result.isEmpty()) {
                    return;
                }
                //one result, check if it is the same entity, if not, it is not unique
                Long idOfEntityToBeValidated = (Long) entityToBeValidated.getClass().getMethod("getId").invoke(entityToBeValidated);
                ENTITY entityFromDb = result.get(0);
                Long idOfEntityFromDb = (Long) entityFromDb.getClass().getMethod("getId").invoke(entityFromDb);
                if (Objects.equals(idOfEntityFromDb, idOfEntityToBeValidated)) {
                    //same entity => unique
                    return;
                }
                //not same entity => not unique => collect error messages
                constraintViolationMessages.add(messageUtil.getMessage(fieldName + "AlreadyTaken", valueOfFieldOfEntity));
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        });
        return constraintViolationMessages;
    }

    /**
     * Query by example
     * <br>
     * The exampleEntity will have only one field
     * <br>
     * Similar to existByHashCode but with this approach, we don't have to declare existBy ... method
     * <br>
     *
     */

    private <ENTITY> List<ENTITY> queryEntityInDbByExample(
            Class<ENTITY> entityType, JpaRepository<ENTITY, ?> entityRepository, String fieldName, String valueOfFieldOfEntity
    ) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        String fieldNameUpperCaseFirstLetter = CommonUtil.upperCaseFirstLetter(fieldName);
        ENTITY exampleEntity = entityType.getConstructor().newInstance();
        //exampleEntity.setHashCode(valueOfFieldOfEntity)
        entityType.getMethod("set" + fieldNameUpperCaseFirstLetter, String.class).invoke(exampleEntity, valueOfFieldOfEntity);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher(fieldName, ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("*")
                .withNullHandler(ExampleMatcher.NullHandler.IGNORE);
        Example<ENTITY> example = Example.of(exampleEntity, exampleMatcher);
        return entityRepository.findAll(example);
    }

    /**
     * Get the name of the list of unique properties and upper case the first letter of that
     * <pre>
     *     For example:
     * &#064;Entity
     * &#064;Table(name = "equipments", uniqueConstraints = {
     * &#064;UniqueConstraint(columnNames = {"serial"}, name = "serial"),
     * &#064;UniqueConstraint(columnNames = {"code"}, name = "code"),
     * &#064;UniqueConstraint(columnNames = {"hashCode"}, name = "hash_code")
     * </pre>
     * => unique properties: serial, code, hashCode
     *
     * @return list of unique properties and upper case their first letter
     */

    public <E> List<String> getUniqueFieldNamesFromEntity(E entity) {
        return Arrays.stream(entity.getClass().getAnnotation(Table.class).uniqueConstraints())
                .map(uniqueConstraint -> uniqueConstraint.columnNames()[0])
                .collect(Collectors.toList());
    }

    /**
     * Validate unique on list of entity, this method will be used in batch create or batch update
     * <br>
     * Using CompletableFuture to validate unique in parallel, this will improve the performance significantly
     *
     * @param entityList          list of entity to be validated
     * @param entityJpaRepository entity repository
     * @param <ENTITY>            entity type
     *
     * @return list of error messages
     */
    public <ENTITY> List<String> validateUniqueOnListOfEntity(List<ENTITY> entityList, JpaRepository<ENTITY, ?> entityJpaRepository) {
        List<String> errorMessages = new ArrayList<>();
        List<CompletableFuture<Void>> validateUniqueTasks = new ArrayList<>();
        for (int index = 0; index < entityList.size(); index++) {
            //finalIndex is used to avoid the concurrency issue
            int finalIndex = index;
            CompletableFuture<Void> validateUniqueTask = CompletableFuture.runAsync(() -> {
                List<String> validationMessages = validateUnique(entityList.get(finalIndex), entityJpaRepository);
                if (!validationMessages.isEmpty()) {
                    errorMessages.add(String.format("%d: %s", finalIndex, validationMessages.stream().parallel().collect(Collectors.joining(", "))));
                }
            });
            validateUniqueTasks.add(validateUniqueTask);
        }
        CompletableFuture.allOf(validateUniqueTasks.toArray(CompletableFuture[]::new)).join();
        return errorMessages;
    }

}
