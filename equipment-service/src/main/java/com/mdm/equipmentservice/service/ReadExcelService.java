package com.mdm.equipmentservice.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public interface ReadExcelService<T> {
    @PreAuthorize("hasAuthority(\"EQUIPMENT.CREATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    public List<T> readExcelFile(InputStream inputStream, String excelFile, Class<T> tClass) throws Exception;
}
