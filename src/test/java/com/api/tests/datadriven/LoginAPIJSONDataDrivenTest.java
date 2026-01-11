package com.api.tests.datadriven;

import com.api.request.model.UserCredentials;
import com.api.services.AuthService;
import com.dataproviders.api.bean.UserBean;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.io.IOException;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
@Listeners(com.listeners.APITestListener.class)
public class LoginAPIJSONDataDrivenTest {
    private UserCredentials userCredentials;
    private AuthService authService;

    @BeforeMethod(description = "Initializing AuthService")
    public void setup() {
        authService = new AuthService();
    }

    @Test(description = "Verifying if Login API is working for user: Front Desk",
            groups = {"api", "regression", "datadriven"},
            dataProviderClass = com.dataproviders.DataProviderUtils.class,
            dataProvider = "LoginAPIJsonDataProvider")
    public void loginAPITest(UserBean userCredentials) throws IOException {
        authService.login(userCredentials)
                .then()
                .spec(responseSpec_OK())
                .body("message", equalTo("Success"))
                .body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));

    }
}
