package com.mdm.equipmentservice;

import org.json.JSONArray;

import java.util.Iterator;

public class SeedDepartment {
    public static void main(String[] args) {
        String departmentData = """
                              [
                  {
                    "name": "Phòng Vật tư thiết bị y tế",
                    "phone": "+84909567432",
                    "alias": "PVTBTYT",
                    "email": "PhongVatTuThietBiYTe@gmail.com",
                    "address": "123 ABC Street, Ho Chi Minh City, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa kiểm soát nhiễm khuẩn",
                    "phone": "+84905578901",
                    "alias": "KKSNNK",
                    "email": "KhoaKiemSoatNhiemKhuan@gmail.com",
                    "address": "456 XYZ Street, Hanoi, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Chẩn đoán hình ảnh",
                    "phone": "+84903215678",
                    "alias": "KCDHA",
                    "email": "KhoaChanDoanHinhAnh@gmail.com",
                    "address": "789 PQR Street, Da Nang, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Hóa Sinh",
                    "phone": "+84904123678",
                    "alias": "KHS",
                    "email": "KhoaHoaSin@gmail.com",
                    "address": "321 MNO Street, Hue, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Huyết học truyền máu",
                    "phone": "+84901472536",
                    "alias": "KHHHTM",
                    "email": "KhoaHuyetHocTruyenMau@gmail.com",
                    "address": "654 JKL Street, Nha Trang, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Vi Sinh",
                    "phone": "+84908246127",
                    "alias": "KVS",
                    "email": "KhoaViSinh@gmail.com",
                    "address": "987 DEF Street, Vung Tau, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Giải phẫu bệnh",
                    "phone": "+84907321564",
                    "alias": "KGPB",
                    "email": "KhoaGiaiPhauBenh@gmail.com",
                    "address": "456 XYZ Street, Hanoi, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Khám Bệnh",
                    "phone": "+84908263417",
                    "alias": "KKKB",
                    "email": "KhoaKhamBenh@gmail.com",
                    "address": "789 PQR Street, Da Nang, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Mắt",
                    "phone": "+84906247539",
                    "alias": "KM",
                    "email": "KhoaMat@gmail.com",
                    "address": "321 MNO Street, Hue, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Răng Hàm Mặt",
                    "phone": "+84903124758",
                    "alias": "KRHM",
                    "email": "KhoaRangHamMat@gmail.com",
                    "address": "654 JKL Street, Nha Trang, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Y học cổ truyền",
                    "phone": "+84901325476",
                    "alias": "KYHCT",
                    "email": "KhoaYHocCoTruyen@gmail.com",
                    "address": "987 DEF Street, Vung Tau, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Lọc Máu",
                    "phone": "+84908247563",
                    "alias": "KLM",
                    "email": "KhoaLocMau@gmail.com",
                    "address": "123 ABC Street, Ho Chi Minh City, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Cấp cứu",
                    "phone": "+84905573629",
                    "alias": "KCC",
                    "email": "KhoaCapCuu@gmail.com",
                    "address": "456 XYZ Street, Hanoi, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Hồi sức tích cực",
                    "phone": "+84903254768",
                    "alias": "KHSTC",
                    "email": "KhoaHoiSucTichCuc@gmail.com",
                    "address": "789 PQR Street, Da Nang, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Nội tổng hợp",
                    "phone": "+84901427635",
                    "alias": "KNTH",
                    "email": "KhoaNoiTongHop@gmail.com",
                    "address": "321 MNO Street, Hue, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Tim mạch",
                    "phone": "+84908253417",
                    "alias": "KTM",
                    "email": "KhoaTimMach@gmail.com",
                    "address": "654 JKL Street, Nha Trang, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Truyền nhiễm",
                    "phone": "+84907315264",
                    "alias": "KTN",
                    "email": "KhoaTruyenNhiem@gmail.com",
                    "address": "987 DEF Street, Vung Tau, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Phẩu thuật gây mê",
                    "phone": "+84906273519",
                    "alias": "KPTGM",
                    "email": "KhoaPhauThuatGayMe@gmail.com",
                    "address": "123 ABC Street, Ho Chi Minh City, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Sản",
                    "phone": "+84909532741",
                    "alias": "KS",
                    "email": "KhoaSan@gmail.com",
                    "address": "456 XYZ Street, Hanoi, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Phụ khoa",
                    "phone": "+84905512376",
                    "alias": "KPK",
                    "email": "KhoaPhuKhoa@gmail.com",
                    "address": "789 PQR Street, Da Nang, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Ung Bướu",
                    "phone": "+84903275648",
                    "alias": "KUB",
                    "email": "KhoaUngBuou@gmail.com",
                    "address": "321 MNO Street, Hue, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Chấn Thương Chỉnh Hình",
                    "phone": "+84901427635",
                    "alias": "KCTCHH",
                    "email": "KhoaChanThuongChinhHinh@gmail.com",
                    "address": "654 JKL Street, Nha Trang, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa thần kinh lồng ngực",
                    "phone": "+84908253471",
                    "alias": "KTKLN",
                    "email": "KhoaThanKinhLongNguc@gmail.com",
                    "address": "987 DEF Street, Vung Tau, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Ngoại Tiết Niệu",
                    "phone": "+84907321564",
                    "alias": "KNTN",
                    "email": "KhoaNgoaiTietNieu@gmail.com",
                    "address": "123 ABC Street, Ho Chi Minh City, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Tiêu Hóa",
                    "phone": "+84906273419",
                    "alias": "KTH",
                    "email": "KhoaTieuHoa@gmail.com",
                    "address": "456 XYZ Street, Hanoi, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Nhi",
                    "phone": "+84909123756",
                    "alias": "KN",
                    "email": "KhoaNhi@gmail.com",
                    "address": "789 PQR Street, Da Nang, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa BV ĐK Quốc Tế",
                    "phone": "+84905572316",
                    "alias": "KBVDKT",
                    "email": "KhoaBVDKQuocTe@gmail.com",
                    "address": "321 MNO Street, Hue, Vietnam",
                    "activeStatus": "ACTIVE"
                  },
                  {
                    "name": "Khoa Dinh dưỡng",
                    "phone": "+84909152736",
                    "alias": "KDD",
                    "email": "KhoaDinhDuong@gmail.com",
                    "address": "654 JKL Street, Nha Trang, Vietnam",
                    "activeStatus": "ACTIVE"
                  }
                ]
                              """;
        JSONArray jsonArray = new JSONArray(departmentData);
        String finalIntellijHttpClientString = "";
        int index = 0;
        Iterator<Object> iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            finalIntellijHttpClientString = finalIntellijHttpClientString.concat("""

                    ###
                    POST http://localhost:8002/api/v1/departments
                    Content-Type: application/json
                    Authorization: Bearer {{auth_token}}
                                        
                                        """);
            try{
                finalIntellijHttpClientString = finalIntellijHttpClientString.concat(jsonArray.getJSONObject(index).toString());
            }catch (Exception e){
                break;
            }
            index++;
        }
        System.out.println(finalIntellijHttpClientString);
    }
}
