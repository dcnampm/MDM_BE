package com.mdm.equipmentservice.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.Year;

public class YearWriteConverter implements Converter<String, Year> {

    // convert String to Year for MongoDB
    @Override
    public Year convert(String source) {
        return Year.parse(source);
    }
}
