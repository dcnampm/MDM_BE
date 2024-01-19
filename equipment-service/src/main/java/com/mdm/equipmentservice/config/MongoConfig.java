//package com.mdm.equipmentservice.config;
//
//import com.mdm.equipmentservice.converter.YearReadConverter;
//import com.mdm.equipmentservice.converter.YearWriteConverter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
//import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
//
//import java.util.ArrayList;
//import java.util.List;
//@Configuration
//@ComponentScan(basePackages = "com.mdm.equipmentservice.model.mongo")
//public class MongoConfig extends AbstractMongoClientConfiguration {
//
//    @Value("${spring.data.mongodb.database}")
//    String database;
//    @Override
//    protected String getDatabaseName() {
//        return database;
//    }
//    private final List<Converter<?, ?>> converters = new ArrayList<>();
//
//    @Override
//    public MongoCustomConversions customConversions() {
//        converters.add(new YearReadConverter());
//        converters.add(new YearWriteConverter());
//        return new MongoCustomConversions(converters);
//    }
//}
// commit by PVH
