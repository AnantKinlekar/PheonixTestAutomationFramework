package com.api.tests;

import static io.restassured.RestAssured.*;
import static com.api.constant.Role.*;
import static com.api.utils.SpecUtil.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;

import com.api.constant.*;
import com.api.request.model.*;

import static com.api.utils.DateTimeUtil.*;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class CreateJobAPITest {

    @Test
    public void createJobAPITTest() {
        Customer customer = new Customer("Anant", "Kinlekar", "7995924124", "", "anantkinlekar18@gmail.com", "");
        CustomerAddress customerAddress = new CustomerAddress("602", "Vasavi Arcade", "Munneshwar Temple Road", "ECC Road", "Paatandur Agrahara", "416410", "India", "Maharashtra");
        CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(1), "23882930780281", "23882930780281", "23882930780281", getTimeWithDaysAgo(1), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
        Problems problems = new Problems(Problem.OVERHEATING.getCode(), "Battery issue");
        List<Problems> problemsList = new ArrayList<>();
        problemsList.add(problems);

        CreateJobPayload createJobPayload = new CreateJobPayload(
                Service_Location.SERVICE_LOCATION_A.getCode(),
                Platform.FRONT_DESK.getCode(),
                Warranty_Status.IN_WARRANTY.getCode(),
                OEM.GOOGLE.getCode(),
                customer,
                customerAddress,
                customerProduct,
                problemsList);

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
