package com.api.tests;

import static com.api.utils.SpecUtil.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import com.api.services.MasterService;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static com.api.constant.Role.*;
import static io.restassured.RestAssured.*;

@Listeners(com.listeners.APITestListener.class)
@Epic("User Management")
@Feature("Master API")
public class MasterAPIRequestTest {
    private MasterService masterService;

    @BeforeMethod(description = "Initializing MasterService")
    public void setup() {
        masterService = new MasterService();
    }
    @Story("Master API should bring OEM details")
    @Description("Verify Master API brings OEM details")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Verify if the MasterAPI is showing correct details in response", groups = {"api", "regression", "smoke"})
    public void masterAPIRequestTest() {
        masterService.master(FD)
                .then()
                .spec(responseSpec_OK())
                .body("message", equalTo("Success"))
                .body("data", notNullValue())
                .body("data", hasKey("mst_oem"))
                .body("data", hasKey("mst_model"))
                .body("data", hasKey("mst_action_status"))
                .body("data", hasKey("mst_warrenty_status"))
                .body("data", hasKey("mst_platform"))
                .body("data", hasKey("mst_product"))
                .body("data", hasKey("mst_role"))
                .body("data", hasKey("mst_service_location"))
                .body("data", hasKey("mst_problem"))
                .body("data", hasKey("map_fst_pincode"))
                .body("data.mst_oem.size()", equalTo(2))
                .body("data.mst_model.size()", equalTo(3))
                .body("data.mst_oem.id", everyItem(notNullValue()))
                .body("data.mst_oem.name", everyItem(notNullValue()))
                .body(matchesJsonSchemaInClasspath("response-schema/MasterAPIRequestResponseSchema.json"));

    }

    @Test(description = "Verify if the Master Api is giving correct status code for invalid token", groups = {"api", "negative", "regression", "smoke"})
    public void invalidTokenMasterAPIRequestTest() {
        given()
                .spec(requestSpec())
                .when()
                .post("master")
                .then()
                .spec(responseSpec_TEXT(401));
    }
}
