package com.api.tests;
import com.api.pojo.UserCredentials;
import static com.api.utils.ConfigManager.*;
import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import org.testng.annotations.Test;
import java.io.IOException;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
public class LoginAPITest {


    @Test
    public static void loginAPITest() throws IOException {
        UserCredentials userCredentials = new UserCredentials("iamfd", "password");

        given()
                .baseUri(getProperty("BASE_URI"))
                .and()
                .contentType(ContentType.JSON)
                .and()
                .accept(ContentType.JSON)
                .and()
                .body(userCredentials)
                .log().uri()
                .log().method()
                .log().headers()
                .log().body()
                .when()
                .post("/login")
                .then()
                .log().body()
                .and()
                .statusCode(200)
                .and()
                .time(lessThan(1000L))
                .and()
                .body("message", equalTo("Success"))
                .and()
                .body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));

    }
}
