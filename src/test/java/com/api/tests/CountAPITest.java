package com.api.tests;

import static com.api.constant.Role.*;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;
import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class CountAPITest {

    @Test
    public void verifyCountAPIResponse(){
        given()
                .baseUri(getProperty("BASE_URI"))
                .and()
                .header("Authorization", getToken(FD))
                .and()
                .accept(ContentType.JSON)
                .when()
                .get("dashboard/count")
                .then()
                .log().body()
                .and()
                .statusCode(200)
                .and()
                .time(lessThan(1000L))
                .and()
                .body("message", equalTo("Success"))
                .and()
                .body("data", notNullValue())
                .and()
                .body("data.size()",equalTo(3))
                .and()
                .body("data.count", everyItem(greaterThanOrEqualTo(0)))
                .body("data.label", everyItem(not(blankOrNullString())))
                .and()
                .body("data.key", everyItem(not(blankOrNullString())))
                .and()
                .body("data.key", containsInAnyOrder("pending_for_delivery", "pending_fst_assignment", "created_today"))
                .and()
                .body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema.json"));
    }

    @Test
    public void countAPITest_MissingAuthToken(){
        given()
                .baseUri(getProperty("BASE_URI"))
                .log().method()
                .log().body()
                .log().headers()
                .when()
                .get("dashboard/count")
                .then()
                .statusCode(401);

    }
}
