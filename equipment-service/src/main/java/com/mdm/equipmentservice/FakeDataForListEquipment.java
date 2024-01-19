package com.mdm.equipmentservice;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FakeDataForListEquipment {

    public static void main(String[] args) {
        String json = """
                {
                  "code": "string",
                  "hashCode": "string",
                  "riskLevel": "A",
                  "technicalParameter": "string",
                  "warehouseImportDate": "2023-06-02",
                  "yearOfManufacture": 2022,
                  "yearInUse": 2022,
                  "configuration": "string",
                  "importPrice": 0,
                  "initialValue": 0,
                  "annualDepreciation": 0,
                  "usageProcedure": "string",
                  "note": "string",
                  "manufacturer": "string",
                  "manufacturingCountry": "string",
                  "categoryId": null,
                  "departmentId": null,
                  "regularMaintenance": 0,
                  "regularInspection": 0,
                  "regularRadiationInspection": 0,
                  "regularExternalQualityAssessment": 0,
                  "regularClinicEnvironmentInspection": 0,
                  "regularCVRadiation": 0,
                  "jointVentureContractExpirationDate": "2023-06-02",
                  "warrantyExpirationDate": "2023-06-02",
                  "projectId": null,
                  "supplierId": null,
                  "name": "string",
                  "model": "string",
                  "serial": "string",
                  "status": "NEWS"
                }""";

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(new JSONObject(json));
        for (int i = 0; i < 20000; i++) {
            String newJson = json.replace("\"string\"", "\"string " + i + "\"");
            jsonArray.put(new JSONObject(newJson));
        }
        String filePath = "seedDataEquipment.json";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(jsonArray.toString());
            System.out.println("JSON array written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
