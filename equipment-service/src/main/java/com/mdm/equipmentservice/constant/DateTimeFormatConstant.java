package com.mdm.equipmentservice.constant;

import java.time.format.DateTimeFormatter;

public class DateTimeFormatConstant {
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter DATE_EXCEL_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
}
