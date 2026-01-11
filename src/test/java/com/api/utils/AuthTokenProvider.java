package com.api.utils;

import static com.api.constant.Role.*;
import com.api.constant.Role;
import com.api.request.model.UserCredentials;
import io.restassured.http.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import static org.hamcrest.Matchers.*;
import static com.api.utils.ConfigManager.*;
import static io.restassured.RestAssured.*;

public class AuthTokenProvider {

    private static Map<Role, String> tokenCache = new ConcurrentHashMap<>();
    private static final Logger LOGGER = LogManager.getLogger(AuthTokenProvider.class);

    private AuthTokenProvider() {

    }

    public static String getToken(Role role) {

        LOGGER.info("Checking if the token for {} is present in the cache", role);
        if (tokenCache.containsKey(role)) {
            LOGGER.info("Token found for Role: {} in the cache", role);
            return tokenCache.get(role);
        }
        LOGGER.info("Token NOT found for Role: {} in the cache for making login request", role);

        UserCredentials userCredentials = null;

        if (role == FD) {
            userCredentials = new UserCredentials("iamfd", "password");
        } else if (role == SUP) {
            userCredentials = new UserCredentials("iamsup", "password");
        } else if (role == ENG) {
            userCredentials = new UserCredentials("iameng", "password");
        } else if (role == QC) {
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
        LOGGER.info("Token cached for the Role: {} for future request", role);
        tokenCache.put(role, token);
        return token;
    }
}
