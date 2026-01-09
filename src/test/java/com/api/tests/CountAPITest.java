package com.api.tests;

import static com.api.constant.Role.*;
import static com.api.utils.SpecUtil.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import com.api.services.DashboardService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

public class CountAPITest {

    private DashboardService dashboardService;

    @BeforeMethod(description = "Initializing DashBoard Service")
    public void setup() {
        dashboardService = new DashboardService();
    }

    @Test(description = "Verify if Count Api is showing correct details in the response", groups = {"api", "regression", "smoke"})
    public void verifyCountAPIResponse(){
        dashboardService.count(FD)
                .then()
                .spec(responseSpec_OK())
                .and()
                .body("message", equalTo("Success"))
                .and()
                .body("data", notNullValue())
                .and()
                .body("data.size()",equalTo(3))
                .and()
                .body("data.count", everyItem(greaterThanOrEqualTo(0)))
                .body("data.label", everyItem(not(blankOrNullString())))
                .and()
                .body("data.key", everyItem(not(blankOrNullString())))
                .and()
                .body("data.key", containsInAnyOrder("pending_for_delivery", "pending_fst_assignment", "created_today"))
                .and()
                .body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema.json"));
    }

    @Test(description = "Verify if Count Api is showing correct status code for invalid token", groups = {"api", "negative", "regression", "smoke"})
    public void countAPITest_MissingAuthToken(){
        dashboardService.countWithNoAuth()
                .then()
                .spec(responseSpec_TEXT(401));

    }
}
