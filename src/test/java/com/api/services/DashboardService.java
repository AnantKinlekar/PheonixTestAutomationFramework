package com.api.services;

import com.api.constant.Role;
import io.restassured.response.Response;
import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

public class DashboardService {
    private static final String COUNT_ENDPOINT = "dashboard/count";

    public Response countWithNoAuth(){
        return given().spec(requestSpec())
                .when()
                .get(COUNT_ENDPOINT);

    }

    public Response count(Role role){
        return given()
                .spec(requestSpecWithAuth(role))
                .when()
                .get(COUNT_ENDPOINT);
    }
}
