package com.api.tests;
import static com.api.constant.Role.*;
import static com.api.utils.ConfigManager.*;

import com.api.constant.Role;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.testng.annotations.Test;
import java.io.IOException;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static com.api.utils.AuthTokenProvider.*;

public class UserDetailsAPITest {

    @Test
    public void userDetailsAPITest() throws IOException {

        Header authHeader = new Header("Authorization", getToken(FD));
        given()
                .baseUri(getProperty("BASE_URI"))
                .and()
                .header(authHeader)
                .and()
                .accept(ContentType.JSON)
                .and()
                .log().uri()
                .log().method()
                .log().body()
                .log().headers()
                .when()
                .get("userdetails")
                .then()
                .log().body()
                .statusCode(200)
                .and()
                .time(lessThan(1000L))
                .and()
                .body("message", equalTo("Success"))
                .and()
                .body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"))
                .and()
                .body("data.id", equalTo(4));
    }
}
