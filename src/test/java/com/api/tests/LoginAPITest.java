package com.api.tests;

import com.api.request.model.UserCredentials;

import static com.api.utils.SpecUtil.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import com.api.services.AuthService;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class LoginAPITest {
    private UserCredentials userCredentials;
    private AuthService authService;

    @BeforeMethod(description = "Create the payload for the Login API")
    public void setup() {
        userCredentials = new UserCredentials("iamfd", "password");
        authService = new AuthService();
    }

    @Test(description = "Verifying if Login API is working for user: Front Desk", groups = {"api", "regression", "smoke"})
    public void loginAPITest() {
        authService.login(userCredentials)
                .then()
                .spec(responseSpec_OK())
                .body("message", equalTo("Success"))
                .body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));

    }
}
