package com.api.tests;
import com.api.request.model.UserCredentials;
import static com.api.utils.SpecUtil.*;
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
                .spec(requestSpec(userCredentials))
                .when()
                .post("/login")
                .then()
                .spec(responseSpec_OK())
                .body("message", equalTo("Success"))
                .body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));

    }
}
