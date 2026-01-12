package com.api.tests;

import static com.api.utils.SpecUtil.*;

import static com.api.constant.Role.*;
import com.api.services.UserService;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;

@Listeners(com.listeners.APITestListener.class)
@Epic("User Management")
@Feature("User Details")
public class UserDetailsAPITest {
    private UserService userService;

    @BeforeMethod(description = "Initializing UserService")
    public void setup() {
        userService = new UserService();
    }

    @Story("Valid user details should be shown")
    @Description("Verify if Valid user details should be shown")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify if the User Details API response is showing correctly for FD User", groups = {"api", "smoke", "regression"})
    public void userDetailsAPITest() {


        userService.userDetails(FD)
                .then()
                .spec(responseSpec_OK())
                .and()
                .body("message", equalTo("Success"))
                .and()
                .body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"))
                .and()
                .body("data.id", equalTo(4));
    }
}
