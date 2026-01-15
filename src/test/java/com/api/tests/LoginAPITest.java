package com.api.tests;

import static com.api.utils.SpecUtil.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import com.api.services.AuthService;
import com.dataproviders.api.bean.UserBean;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

@Listeners(com.listeners.APITestListener.class)
@Epic("User Management")
@Feature("Authentication")
public class LoginAPITest {
    private UserBean userCredentials;
    private AuthService authService;

    @BeforeMethod(description = "Create the payload for the Login API and Initializing AuthService")
    public void setup() {
        userCredentials = new UserBean("iamfd", "password");
        authService = new AuthService();
    }

    @Story("Valid user should be able to login into the system")
    @Description("Verify if the FD user is able to login via API")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Verifying if Login API is working for user: Front Desk", groups = {"api", "regression", "smoke"}, retryAnalyzer = com.api.retry.RetryAnalyzer.class)
    public void loginAPITest() {
        authService.login(userCredentials)
                .then()
                .spec(responseSpec_OK())
                .body("message", equalTo("Success"))
                .body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));

    }
}
