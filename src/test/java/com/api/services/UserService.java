package com.api.services;

import com.api.constant.Role;
import io.restassured.response.Response;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

public class UserService {

    private static final String USER_DETAILS_ENDPOINT = "/userdetails";
    public Response userDetails(Role role) {
        return given()
                .spec(requestSpecWithAuth(role))
                .when()
                .get(USER_DETAILS_ENDPOINT);
    }
}
