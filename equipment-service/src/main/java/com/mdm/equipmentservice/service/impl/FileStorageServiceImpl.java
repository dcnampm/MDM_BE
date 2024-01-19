package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.exception.ResourceNotFoundException;
import com.mdm.equipmentservice.mapper.FileStorageMapper;
import com.mdm.equipmentservice.model.dto.base.FileStorageDto;
import com.mdm.equipmentservice.model.entity.FileDescription;
import com.mdm.equipmentservice.model.entity.FileStorage;
import com.mdm.equipmentservice.model.entity.User;
import com.mdm.equipmentservice.model.repository.FileStorageRepository;
import com.mdm.equipmentservice.model.repository.UserRepository;
import com.mdm.equipmentservice.query.param.GetFileStoragesQueryParam;
import com.mdm.equipmentservice.query.predicate.FileStoragePredicate;
import com.mdm.equipmentservice.service.FileStorageService;
import com.mdm.equipmentservice.util.CommonUtil;
import com.mdm.equipmentservice.util.MessageUtil;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mdm.equipmentservice.query.predicate.FileStoragePredicate.matchFileDescription;
import static com.mdm.equipmentservice.query.predicate.FileStoragePredicate.whereIdIn;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final FileStorageRepository fileStorageRepository;

    private final MessageUtil messageUtil;

    private final UserRepository userRepository;

    private final FileStorageMapper fileStorageMapper;

    @Autowired
    public FileStorageServiceImpl(
            FileStorageRepository fileStorageRepository, MessageUtil messageUtil, UserRepository userRepository,
            FileStorageMapper fileStorageMapper
    ) {
        this.fileStorageRepository = fileStorageRepository;
        this.messageUtil = messageUtil;
        this.userRepository = userRepository;
        this.fileStorageMapper = fileStorageMapper;
    }

    @Override
    @Transactional
    public List<FileStorage> uploadMultipleFiles(
            String associatedEntityType, Long associatedEntityId, FileDescription fileDescription, MultipartFile... multipartFiles
    ) {
        if (multipartFiles == null || multipartFiles.length == 0) {
            return new ArrayList<>();
        }
        List<MultipartFile> multipartFileList = Arrays.asList(multipartFiles);
        List<FileStorage> uploadedFiles = new ArrayList<>();
        multipartFileList.forEach(file -> {
            if (file == null || file.isEmpty()) {
                return;
            }
            try {
                FileStorage fileStorage = FileStorage.builder()
                        .name(CommonUtil.getFileName(file))
                        .extension(CommonUtil.getFileExtension(file))
                        .associatedEntityId(associatedEntityId)
                        .associatedEntityType(associatedEntityType)
                        .description(fileDescription)
                        .data(file.getBytes())
                        .contentType(file.getContentType())
                        .build();
                fileStorage = fileStorageRepository.save(fileStorage);
                uploadedFiles.add(fileStorage);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        return uploadedFiles;
    }

    @Override
    public FileStorage getImageById(Long id) {
        FileStorage fileStorage = fileStorageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("imageNotFound")));
        if (!fileStorage.getDescription().equals(FileDescription.IMAGE)) {
            throw new ResourceNotFoundException(messageUtil.getMessage("imageNotFound"));
        } else {
            return fileStorage;
        }
    }

    @Override
    public FileStorage getDocumentById(Long id) {
        FileStorage fileStorage = fileStorageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("documentNotFound")));
        if (!fileStorage.getDescription().equals(FileDescription.DOCUMENT)) {
            throw new ResourceNotFoundException(messageUtil.getMessage("documentNotFound"));
        } else {
            return fileStorage;
        }
    }

    /**
     * Get all files of an entity by associatedEntityType and associatedEntityId
     *
     * @param associatedEntityType (e.g. "User")
     * @param associatedEntityId   (e.g. 1)
     * @param fileDescription      (e.g. FileDescription.IMAGE), if null, get all files (images and documents)
     * @return List of FileStorage
     */
    @Override
    public List<FileStorage> getAllFilesOfAnEntity(String associatedEntityType, Long associatedEntityId, FileDescription fileDescription) {
        return fileStorageRepository.findAll(FileStoragePredicate.getFileStoragePredicate(new GetFileStoragesQueryParam(
                associatedEntityId,
                associatedEntityType,
                fileDescription
        )));
    }

    /**
     * similar to {@link FileStorageServiceImpl#getAllFilesOfAnEntity(String, Long, FileDescription)} but return {@link FileStorageDto} instead of
     * {@link FileStorage}
     *
     * @param associatedEntityType
     * @param associatedEntityId
     * @param fileDescription
     * @return
     */
    @Override
    public List<FileStorageDto> getAllFilesOfAnEntityWithoutData(String associatedEntityType, Long associatedEntityId, FileDescription fileDescription) {
        return getAllFilesOfAnEntity(associatedEntityType, associatedEntityId, fileDescription).parallelStream().map(fileStorageMapper::toDto).toList();
    }

    @Override
    public List<Long> getAllFileIdsOfAnEntity(String associatedEntityType, Long associatedEntityId, FileDescription fileDescription) {
        return getAllFilesOfAnEntity(associatedEntityType, associatedEntityId, fileDescription).parallelStream().map(FileStorage::getId).toList();
    }

    /**
     * Delete all files of an entity by associatedEntityType and associatedEntityId
     *
     * @param associatedEntityType (e.g. "User")
     * @param associatedEntityId   (e.g. 1)
     * @param fileDescription      (e.g. FileDescription.IMAGE), if null, delete all files (images and documents)
     */
    @Override
    public void deleteAllFilesOfAnEntity(String associatedEntityType, Long associatedEntityId, FileDescription fileDescription) {
        List<FileStorage> fileStorages = fileStorageRepository.findAll(FileStoragePredicate.getFileStoragePredicate(new GetFileStoragesQueryParam(
                associatedEntityId,
                associatedEntityType,
                fileDescription
        )));
        fileStorageRepository.deleteAll(fileStorages);
    }

    @Override
    public FileStorage uploadFile(MultipartFile file, FileDescription fileDescription) throws IOException {
        FileStorage fileStorage = FileStorage.builder()
                .name(file.getOriginalFilename())
                .contentType(file.getContentType())
                .extension(CommonUtil.getFileExtension(file))
                .description(fileDescription)
                .associatedEntityType(User.class.getSimpleName())
                .associatedEntityId(1L).data(file.getBytes())
                .build();
        return fileStorageRepository.saveAndFlush(fileStorage);
    }

    @Override
    public List<FileStorage> getDocumentsByIdIn(List<Long> ids) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        return fileStorageRepository.findAll(booleanBuilder.and(whereIdIn(ids)).and(matchFileDescription(FileDescription.DOCUMENT)));
    }

    @Override
    public List<FileStorageDto> getDocumentsDetailWithoutDataByIdIn(List<Long> ids) {
        return getDocumentsByIdIn(ids).stream().map(fileStorageMapper::toDto).toList();
    }

    @Override
    public List<FileStorage> getImagesByIdIn(List<Long> ids) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        return fileStorageRepository.findAll(booleanBuilder.and(whereIdIn(ids)).and(matchFileDescription(FileDescription.IMAGE)));
    }
}
