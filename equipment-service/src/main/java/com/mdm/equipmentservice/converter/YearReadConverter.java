package com.mdm.equipmentservice.converter;


import org.springframework.core.convert.converter.Converter;

import java.time.Year;

public class YearReadConverter implements Converter<Year, String> {

    // convert Year to String for MongoDB
    @Override
    public String convert(Year source) {
        return source.toString();
    }
}
