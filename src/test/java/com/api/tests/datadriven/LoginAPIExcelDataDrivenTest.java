package com.api.tests.datadriven;

import com.api.request.model.UserCredentials;
import com.dataproviders.api.bean.UserBean;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class LoginAPIExcelDataDrivenTest {

    @Test(description = "Verifying if Login API is working for user: Front Desk",
            groups = {"api", "regression", "datadriven"},
            dataProviderClass = com.dataproviders.DataProviderUtils.class,
            dataProvider = "LoginAPIExcelDataProvider")
    public void loginAPITest(UserBean userBean) throws IOException {
        given()
                .spec(requestSpec(userBean))
                .when()
                .post("/login")
                .then()
                .spec(responseSpec_OK())
                .body("message", equalTo("Success"))
                .body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));

    }
}
