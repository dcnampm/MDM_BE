//package com.mdm.equipmentservice;
//
//import org.junit.jupiter.api.Test;
//
//import static io.restassured.RestAssured.given;
//
//public class SupplyControllerTest extends KeycloakTestContainer{
//    @Test
//    void givenAuthenticatedUser_whenGetMe_shouldReturnMyInfo() {
//        given()
//                .header("Authorization", getBearerToken())
//                .when()
//                .get("/api/v1/equipments")
//                .then()
//                .statusCode(200);
//
//    }
//}
