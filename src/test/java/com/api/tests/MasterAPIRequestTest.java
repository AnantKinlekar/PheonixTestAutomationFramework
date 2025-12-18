package com.api.tests;

import static com.api.utils.AuthTokenProvider.*;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;
import org.testng.annotations.Test;

import static com.api.constant.Role.*;

import static com.api.utils.ConfigManager.*;

import static io.restassured.RestAssured.*;

public class MasterAPIRequestTest {

    @Test
    public void masterAPIRequestTest(){
        given()
                .baseUri(getProperty("BASE_URI"))
                .and()
                .header("Authorization",getToken(FD))
                .and()
                .contentType("")
                .accept(ContentType.ANY)
                .when()
                .post("master")
                .then()
                .log().body()
                .and()
                .statusCode(200)
                .and()
                .body("message", equalTo("Success"))
                .and()
                .body("data", notNullValue())
                .and()
                .time(lessThan(1000L))
                .and()
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
                .and()
                .body("data.mst_oem.size()", equalTo(2))
                .body("data.mst_model.size()", equalTo(3))
                .and()
                .body("data.mst_oem.id", everyItem(notNullValue()))
                .body("data.mst_oem.name", everyItem(notNullValue()))
                .and()
                .body(matchesJsonSchemaInClasspath("response-schema/MasterAPIRequestResponseSchema.json"));

    }

    @Test
    public void invalidTokenMasterAPIRequestTest(){
        given()
                .baseUri(getProperty("BASE_URI"))
                .and()
                .header("Authorization","")
                .and()
                .contentType("")
                .accept(ContentType.ANY)
                .when()
                .post("master")
                .then()
                .statusCode(401);
    }
}
