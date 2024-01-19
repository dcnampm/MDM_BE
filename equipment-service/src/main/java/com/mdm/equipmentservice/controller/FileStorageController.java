package com.mdm.equipmentservice.controller;

import com.mdm.equipmentservice.model.dto.base.FileStorageDto;
import com.mdm.equipmentservice.model.entity.FileStorage;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.FileStorageService;
import com.mdm.equipmentservice.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/files")
public class FileStorageController {
    private final FileStorageService fileStorageService;

    private final MessageUtil messageUtil;

    @Autowired
    public FileStorageController(FileStorageService fileStorageService, MessageUtil messageUtil) {
        this.fileStorageService = fileStorageService;
        this.messageUtil = messageUtil;
    }

    @GetMapping(value = "/image/{id}")
    public ResponseEntity<GenericResponse<FileStorage>> getImage(@PathVariable Long id) {
        FileStorage fileStorage = fileStorageService.getImageById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GenericResponse<>(fileStorage, 200, messageUtil.getMessage("imageRetrievedSuccessfully")));
    }

    @GetMapping(value = "/document/{id}")
    public ResponseEntity<GenericResponse<FileStorage>> getDocument(@PathVariable Long id) {
        FileStorage fileStorage = fileStorageService.getDocumentById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GenericResponse<>(fileStorage, 200, messageUtil.getMessage("documentRetrievedSuccessfully")));
    }


    @GetMapping(value = "/document")
    public ResponseEntity<GenericResponse<List<FileStorage>>> getDocumentsByIdIn(@RequestParam(name = "id") Long[] ids) {
        List<FileStorage> fileStorages = fileStorageService.getDocumentsByIdIn(List.of(ids));
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(fileStorages));
    }
    @GetMapping(value = "/info")
    public ResponseEntity<GenericResponse<List<FileStorageDto>>> getDocumentsDetailWithoutDataByIdIn(@RequestParam(name = "id") Long[] ids) {
        List<FileStorageDto> fileStorages = fileStorageService.getDocumentsDetailWithoutDataByIdIn(List.of(ids));
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(fileStorages));
    }

    @GetMapping(value = "/image")
    public ResponseEntity<GenericResponse<List<FileStorage>>> getImagesByIdIn(@RequestParam(name = "id") Long[] ids) {
        List<FileStorage> fileStorages = fileStorageService.getImagesByIdIn(List.of(ids));
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(fileStorages));
    }

}
