package com.api.tests;
import static com.api.utils.SpecUtil.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import org.testng.annotations.Test;
import static com.api.constant.Role.*;
import static io.restassured.RestAssured.*;


public class MasterAPIRequestTest {

    @Test
    public void masterAPIRequestTest(){
        given()
                .spec(requestSpecWithAuth(FD))
                .when()
                .post("master")
                .then()
                .spec(responseSpec_OK())
                .body("message", equalTo("Success"))
                .body("data", notNullValue())
                .body("data", hasKey("mst_oem"))
                .body("data", hasKey("mst_model"))
                .body("data", hasKey("mst_action_status"))
                .body("data", hasKey("mst_warrenty_status"))
                .body("data", hasKey("mst_platform"))
                .body("data", hasKey("mst_product"))
                .body("data", hasKey("mst_role"))
                .body("data", hasKey("mst_service_location"))
                .body("data", hasKey("mst_problem"))
                .body("data", hasKey("map_fst_pincode"))
                .body("data.mst_oem.size()", equalTo(2))
                .body("data.mst_model.size()", equalTo(3))
                .body("data.mst_oem.id", everyItem(notNullValue()))
                .body("data.mst_oem.name", everyItem(notNullValue()))
                .body(matchesJsonSchemaInClasspath("response-schema/MasterAPIRequestResponseSchema.json"));

    }

    @Test
    public void invalidTokenMasterAPIRequestTest(){
        given()
                .spec(requestSpec())
                .when()
                .post("master")
                .then()
                .spec(responseSpec_TEXT(401));
    }
}
