package com.api.services;

import io.restassured.response.Response;
import static com.api.utils.SpecUtil.requestSpec;
import static io.restassured.RestAssured.given;

public class AuthService {
    //it will hold the APIs that belong to the Auth
    private static final String LOGIN_ENDPOINT = "/login";

    public Response login(Object userCredentials) {
        return given()
                .spec(requestSpec(userCredentials))
                .when()
                .post(LOGIN_ENDPOINT);

    }
}
