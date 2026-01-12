package com.api.services;

import com.dataproviders.api.bean.UserBean;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.api.utils.SpecUtil.requestSpec;
import static io.restassured.RestAssured.given;

public class AuthService {
    //it will hold the APIs that belong to the Auth
    private static final String LOGIN_ENDPOINT = "/login";
    private static final Logger LOGGER = LogManager.getLogger(AuthService.class);
    @Step("Perform login request with userCredentials")
    public Response login(Object userCredentials) {
        LOGGER.info("Making login request for payload {}", ((UserBean)userCredentials).getUsername());
        return given()
                .spec(requestSpec(userCredentials))
                .when()
                .post(LOGIN_ENDPOINT);

    }
}
