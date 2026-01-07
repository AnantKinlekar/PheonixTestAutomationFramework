package com.api.tests;

import static com.api.constant.Role.*;
import static com.api.utils.SpecUtil.*;

import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class UserDetailsAPITest {

    @Test(description = "Verify if the User Details API response is showing correctly for FD User", groups = {"api", "smoke", "regression"})
    public void userDetailsAPITest() {

        given()
                .spec(requestSpecWithAuth(FD))
                .when()
                .get("userdetails")
                .then()
                .spec(responseSpec_OK())
                .and()
                .body("message", equalTo("Success"))
                .and()
                .body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"))
                .and()
                .body("data.id", equalTo(4));
    }
}
