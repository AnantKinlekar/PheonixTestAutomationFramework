package com.api.services;

import com.api.constant.Role;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

public class UserService {

    private static final String USER_DETAILS_ENDPOINT = "/userdetails";
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    @Step("Making UserDetails API Request")
    public Response userDetails(Role role) {
        LOGGER.info("Making request to the {} with Role: {}", USER_DETAILS_ENDPOINT, role);
        return given()
                .spec(requestSpecWithAuth(role))
                .when()
                .get(USER_DETAILS_ENDPOINT);
    }
}
