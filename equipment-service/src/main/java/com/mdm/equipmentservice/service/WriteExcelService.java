package com.mdm.equipmentservice.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;

@Service
public interface WriteExcelService<T> {
    @PreAuthorize("hasAuthority(\"EQUIPMENT.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void writeExcelFile(List<T> list, OutputStream excelOutputStream, Class<T> tClass);
}
