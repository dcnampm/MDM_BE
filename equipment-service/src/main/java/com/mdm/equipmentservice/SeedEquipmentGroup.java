package com.mdm.equipmentservice;


import org.json.JSONArray;

import java.util.Iterator;

public class SeedEquipmentGroup {
    public static void main(String[] args) {
        String data = """
                [
                  {
                    "id": 1,
                    "name": "N1 Thiết bị chẩn đoán hình ảnh",
                    "alias": "N01",
                    "note": ""
                  },
                  {
                    "id": 2,
                    "name": "N2 Thiết bị hồi sức cấp cứu",
                    "alias": "N02",
                    "note": ""
                  },
                  {
                    "id": 3,
                    "name": "N3 Thiết bị lọc máu",
                    "alias": "N03",
                    "note": ""
                  },
                  {
                    "id": 4,
                    "name": "N4 Thiết bị phòng mổ",
                    "alias": "N04",
                    "note": ""
                  },
                  {
                    "id": 5,
                    "name": "N5 Thiết bị chuyên khoa ung bướu và y học hạt nhân",
                    "alias": "N05",
                    "note": ""
                  },
                  {
                    "id": 6,
                    "name": "N6 Thiết bi chuyên khoa RHM-THM-Mắt",
                    "alias": "N06",
                    "note": ""
                  },
                  {
                    "id": 7,
                    "name": "N7 Thiết bị chuyên khoa xét nghiệm",
                    "alias": "N07",
                    "note": ""
                  },
                  {
                    "id": 8,
                    "name": "N8 Thiết bị kiểm soát nhiễm khuẩn",
                    "alias": "N08",
                    "note": ""
                  },
                  {
                    "id": 9,
                    "name": "N9 Thiết bị chuyên khoa sản nhi",
                    "alias": "N09",
                    "note": ""
                  },
                  {
                    "id": 10,
                    "name": "N10 Thiết bị thăm dò chức năng",
                    "alias": "N10",
                    "note": ""
                  },
                  {
                    "id": 11,
                    "name": "N11 Thiết bị nội soi chuẩn đoán",
                    "alias": "N11",
                    "note": ""
                  },
                  {
                    "id": 12,
                    "name": "N12 Thiết bị hồi phục chức năng- Vật lý trị liệu",
                    "alias": "N12",
                    "note": ""
                  },
                  {
                    "id": 13,
                    "name": "N13 Thiết bị dùng chung",
                    "alias": "N13",
                    "note": ""
                  },
                  {
                    "id": 14,
                    "name": "N14 Thiết bị khám- điều trị chuyên dụng",
                    "alias": "N14",
                    "note": ""
                  }
                ]
                                              """;
        JSONArray jsonArray = new JSONArray(data);
        String finalIntellijHttpClientString = "";
        int index = 0;
        Iterator<Object> iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            finalIntellijHttpClientString = finalIntellijHttpClientString.concat("""

                    ###
                    POST http://localhost:8002/api/v1/equipment-groups
                    Content-Type: application/json
                    Authorization: Bearer {{auth_token}}
                                        
                                        """);
            try {
                finalIntellijHttpClientString = finalIntellijHttpClientString.concat(jsonArray.getJSONObject(index).toString());
            } catch (Exception e) {
                break;
            }
            index++;
        }
        System.out.println(finalIntellijHttpClientString);
    }
}
