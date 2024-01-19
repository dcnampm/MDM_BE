package com.mdm.equipmentservice.constant;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EquipmentExcelConstant {

    public static Map<String, String> importExcelHeader;
    static {
        importExcelHeader = new HashMap<>();
//        headerMap.put("STT", "id");
        importExcelHeader.put("Tên thiết bị", "name");
        importExcelHeader.put("Model", "model");
        importExcelHeader.put("Serial", "serial");
        importExcelHeader.put("Mã thiết bị", "code");
        importExcelHeader.put("HashCode", "hashCode");
        importExcelHeader.put("Mức độ rủi ro", "riskLevel");
        importExcelHeader.put("Thông số kỹ thuật", "technicalParameter");
        importExcelHeader.put("Ngày nhập kho", "warehouseImportDate");
        importExcelHeader.put("Năm sản xuất", "yearOfManufacture");
        importExcelHeader.put("Năm sử dụng", "yearInUse");
        importExcelHeader.put("Cấu hình thiết bị", "configuration");
        importExcelHeader.put("Giá nhập", "importPrice");
        importExcelHeader.put("Giá trị ban đầu", "initialValue");
        importExcelHeader.put("Khấu hao hàng năm", "annualDepreciation");
        importExcelHeader.put("Quy trình sử dụng", "usageProcedure");
        importExcelHeader.put("Ghi chú", "note");
        importExcelHeader.put("Trạng thái", "status");
        importExcelHeader.put("Nhà sản xuất", "manufacturer");
        importExcelHeader.put("Nước sản xuất", "manufacturingCountry");
        importExcelHeader.put("Loại", "categoryId");
//        headerMap.put("Tên nhóm loại thiết bị", "categoryGroupName");
        importExcelHeader.put("Khoa Phòng", "departmentId");
        importExcelHeader.put("Chu kì Bảo trì", "regularMaintenance");
        importExcelHeader.put("Chu kì Kiểm định", "regularInspection");
//        headerMap.put("Chu kì Kiểm xạ", "regularRadiationInspection");
//        headerMap.put("Chu kì Ngoại kiểm", "regularExternalQualityAssessment");
//        headerMap.put("Chu kì kiểm định môi trường phòng", "regularClinicEnvironmentInspection");
//        headerMap.put("Chu kì Gia hạn giấy phép tiến hành CV bức xạ", "regularCVRadiation");
        importExcelHeader.put("Ngày hết hạn hợp đồng LDLK", "jointVentureContractExpirationDate");
        importExcelHeader.put("Ngày hết hạn bảo hành", "warrantyExpirationDate");
        importExcelHeader.put("Dự án", "projectId");
        importExcelHeader.put("Nhà cung cấp", "supplierId");
        importExcelHeader.put("Đơn vị tính", "unitId");
    }
    public static Map<String, String> exportExcelHeader;
    static {
        exportExcelHeader = new LinkedHashMap<>();
//        headerMap.put("STT", "id");
        exportExcelHeader.put("Tên thiết bị", "name");
        exportExcelHeader.put("Model", "model");
        exportExcelHeader.put("Serial", "serial");
        exportExcelHeader.put("Mã thiết bị", "code");
        exportExcelHeader.put("HashCode", "hashCode");
        exportExcelHeader.put("Trạng thái", "status");
        exportExcelHeader.put("Mức độ rủi ro", "riskLevel");
        exportExcelHeader.put("Thông số kỹ thuật", "technicalParameter");
        exportExcelHeader.put("Ngày nhập kho", "warehouseImportDate");
        exportExcelHeader.put("Năm sản xuất", "yearOfManufacture");
        exportExcelHeader.put("Năm sử dụng", "yearInUse");
        exportExcelHeader.put("Cấu hình thiết bị", "configuration");
        exportExcelHeader.put("Giá nhập", "importPrice");
        exportExcelHeader.put("Giá trị ban đầu", "initialValue");
        exportExcelHeader.put("Khấu hao hàng năm", "annualDepreciation");
        exportExcelHeader.put("Quy trình sử dụng", "usageProcedure");
        exportExcelHeader.put("Ghi chú", "note");
        exportExcelHeader.put("Nhà sản xuất", "manufacturer");
        exportExcelHeader.put("Nước sản xuất", "manufacturingCountry");
        exportExcelHeader.put("Loại", "categoryName");
        exportExcelHeader.put("Nhóm thiết bị", "categoryGroupName");
        exportExcelHeader.put("Khoa Phòng", "departmentName");
        exportExcelHeader.put("Chu kì Bảo trì", "regularMaintenance");
        exportExcelHeader.put("Chu kì Kiểm định", "regularInspection");
//        headerMap.put("Chu kì Kiểm xạ", "regularRadiationInspection");
//        headerMap.put("Chu kì Ngoại kiểm", "regularExternalQualityAssessment");
//        headerMap.put("Chu kì kiểm định môi trường phòng", "regularClinicEnvironmentInspection");
//        headerMap.put("Chu kì Gia hạn giấy phép tiến hành CV bức xạ", "regularCVRadiation");
        exportExcelHeader.put("Ngày hết hạn hợp đồng LDLK", "jointVentureContractExpirationDate");
        exportExcelHeader.put("Ngày hết hạn bảo hành", "warrantyExpirationDate");
        exportExcelHeader.put("Dự án", "projectName");
        exportExcelHeader.put("Nhà cung cấp", "supplierName");
        exportExcelHeader.put("Đơn vị tính", "unitName");
    }

}
