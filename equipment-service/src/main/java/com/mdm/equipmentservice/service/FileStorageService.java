package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.base.FileStorageDto;
import com.mdm.equipmentservice.model.entity.FileDescription;
import com.mdm.equipmentservice.model.entity.FileStorage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileStorageService {

    List<FileStorage> uploadMultipleFiles(String associatedEntityType, Long associatedEntityId, FileDescription fileDescription, MultipartFile... multipartFiles);

    FileStorage getImageById(Long id);

    FileStorage getDocumentById(Long id);

    /**
     * Get all files of an entity by associatedEntityType and associatedEntityId
     *
     * @param associatedEntityType (e.g. "User")
     * @param associatedEntityId   (e.g. 1)
     * @param fileDescription      (e.g. FileDescription.IMAGE), if null, get all files (images and documents)
     *
     * @return list of file storage
     */
    List<FileStorage> getAllFilesOfAnEntity(String associatedEntityType, Long associatedEntityId, FileDescription fileDescription);

    List<FileStorageDto> getAllFilesOfAnEntityWithoutData(String associatedEntityType, Long associatedEntityId, FileDescription fileDescription);

    /**
     * Similar to {@link #getAllFilesOfAnEntity(String, Long, FileDescription)} but this method will return a list of ID
     *
     * @param associatedEntityType (e.g. "User")
     * @param associatedEntityId   (e.g. 1)
     * @param fileDescription      (e.g. FileDescription.IMAGE), if null, get all files (images and documents)
     *
     * @return list of file id
     */
    List<Long> getAllFileIdsOfAnEntity(String associatedEntityType, Long associatedEntityId, FileDescription fileDescription);


    /**
     * Delete all files of an entity by associatedEntityType and associatedEntityId
     *
     * @param associatedEntityType (e.g. "User")
     * @param associatedEntityId   (e.g. 1)
     * @param fileDescription      (e.g. FileDescription.IMAGE), if null, delete all files (images and documents)
     */
    void deleteAllFilesOfAnEntity(String associatedEntityType, Long associatedEntityId, FileDescription fileDescription);

    FileStorage uploadFile(MultipartFile file, FileDescription fileDescription) throws IOException;

    List<FileStorage> getDocumentsByIdIn(List<Long> ids);

    List<FileStorageDto> getDocumentsDetailWithoutDataByIdIn(List<Long> ids);

    List<FileStorage> getImagesByIdIn(List<Long> ids);
}
