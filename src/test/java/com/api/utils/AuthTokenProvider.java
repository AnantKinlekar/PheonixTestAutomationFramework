package com.api.utils;
import static com.api.constant.Role.*;
import com.api.constant.Role;
import com.api.request.model.UserCredentials;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;

import static com.api.utils.ConfigManager.*;
import static io.restassured.RestAssured.*;

public class AuthTokenProvider {
    //commenting for test
    private AuthTokenProvider(){

    }
    public static String getToken(Role role) {

        UserCredentials userCredentials = null;

        if(role == FD){
            userCredentials = new UserCredentials("iamfd", "password");
        }
        else if(role == SUP){
            userCredentials = new UserCredentials("iamsup", "password");
        }
        else if(role == ENG){
            userCredentials = new UserCredentials("iameng", "password");
        }
        else if(role == QC){
            userCredentials = new UserCredentials("iamqc", "password");
        }

        String token = given()
                .baseUri(getProperty("BASE_URI"))
                .and()
                .contentType(ContentType.JSON)
                .and()
                .accept(ContentType.JSON)
                .and()
                .body(userCredentials)
                .when()
                .post("login")
                .then()
                .statusCode(200)
                .and()
                .time(lessThan(1000L))
                .and()
                .body("message", equalTo("Success"))
                .and()
                .log().ifValidationFails()
                .extract().body().jsonPath().getString("data.token");
        return token;
    }
}
