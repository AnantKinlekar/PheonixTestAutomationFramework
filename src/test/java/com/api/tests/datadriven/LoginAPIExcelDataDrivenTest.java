package com.api.tests.datadriven;

import com.api.services.AuthService;
import com.dataproviders.api.bean.UserBean;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class LoginAPIExcelDataDrivenTest {

    private AuthService authService;

    @BeforeMethod(description = "Initializing AuthService")
    public void setup() {
        authService = new AuthService();
    }

    @Test(description = "Verifying if Login API is working for user: Front Desk",
            groups = {"api", "regression", "datadriven"},
            dataProviderClass = com.dataproviders.DataProviderUtils.class,
            dataProvider = "LoginAPIExcelDataProvider")
    public void loginAPITest(UserBean userBean) throws IOException {
        authService.login(userBean)
                .then()
                .spec(responseSpec_OK())
                .body("message", equalTo("Success"))
                .body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));

    }
}
