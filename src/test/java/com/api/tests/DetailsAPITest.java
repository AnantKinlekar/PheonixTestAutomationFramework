package com.api.tests;

import static com.api.constant.Role.*;
import com.api.request.model.Detail;
import com.api.services.DashboardService;
import com.api.utils.SpecUtil;
import static org.hamcrest.Matchers.*;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
@Listeners(com.listeners.APITestListener.class)
@Epic("Job Management")
@Feature("Job Details")
public class DetailsAPITest {
    private DashboardService dashboardService;
    private Detail detailPayload;

    @BeforeMethod(description = "Initializing dashboard service and creating details payload")
    public void setup(){
        dashboardService = new DashboardService();
        detailPayload = new Detail("created_today");
    }
    @Story("Job details should be shown correctly")
    @Description("Verify Job details should be shown correctly")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "verify if Details API is working properly", groups = {"api", "smoke", "regression"})
    public void detailAPITest(){
        dashboardService.details(FD,detailPayload)
                .then()
                .spec(SpecUtil.responseSpec_OK())
                .body("message", equalTo("Success"));
    }
}
