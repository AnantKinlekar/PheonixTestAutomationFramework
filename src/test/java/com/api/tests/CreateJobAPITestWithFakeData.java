package com.api.tests;

import com.api.request.model.*;
import com.api.utils.FakerDataGenerator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;


public class CreateJobAPITestWithFakeData {
    private CreateJobPayload createJobPayload;
    private Customer customer;
    private CustomerAddress customerAddress;
    private CustomerProduct customerProduct;
    private Problems problems;
    private List<Problems> problemsList;
    private static final String COUNTRY = "India";



    @BeforeMethod(description = "Creating Create Job Api request Payload")
    public void setup() {
        createJobPayload = FakerDataGenerator.generateFakeCreateJobData();
    }


    @Test(description = "Verify if Create Job Api is creating inwarranty job", groups = {"api", "regression", "smoke"})
    public void createJobAPITTest() {

        given()
                .spec(requestSpecWithAuth(FD, createJobPayload))
                .when()
                .post("/job/create")
                .then()
                .spec(responseSpec_OK())
                .body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
                .body("message", equalTo("Job created successfully. "))
                .body("data.mst_service_location_id", equalTo(1))
                .body("data.mst_platform_id", equalTo(2))
                .body("data.mst_warrenty_status_id", equalTo(1))
                .body("data.mst_oem_id", equalTo(1))
                .body("data.job_number", startsWith("JOB_"));

    }
}
