package com.api.tests;

import static com.api.utils.SpecUtil.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;

import com.api.constant.*;
import com.api.constant.Problem;
import com.api.request.model.*;

import static com.api.utils.DateTimeUtil.*;

import com.api.services.JobService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class CreateJobAPITest {
    private CreateJobPayload createJobPayload;
    private Customer customer;
    private CustomerAddress customerAddress;
    private CustomerProduct customerProduct;
    private Problems problems;
    private List<Problems> problemsList;
    private JobService jobService;


    @BeforeMethod(description = "Creating Create Job Api request Payload and initializing job service")
    public void setup() {
        jobService = new JobService();
        customer = new Customer("Anant", "Kinlekar", "7995924124", "", "anantkinlekar18@gmail.com", "");
        customerAddress = new CustomerAddress("602", "Vasavi Arcade", "Munneshwar Temple Road", "ECC Road", "Paatandur Agrahara", "416410", "India", "Maharashtra");
        customerProduct = new CustomerProduct(getTimeWithDaysAgo(1), "23842930780876", "23842930780876", "23842930780876", getTimeWithDaysAgo(1), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
        problems = new Problems(Problem.OVERHEATING.getCode(), "Battery issue");
        problemsList = new ArrayList<>();
        problemsList.add(problems);

        createJobPayload = new CreateJobPayload(
                Service_Location.SERVICE_LOCATION_A.getCode(),
                Platform.FRONT_DESK.getCode(),
                Warranty_Status.IN_WARRANTY.getCode(),
                OEM.GOOGLE.getCode(),
                customer,
                customerAddress,
                customerProduct,
                problemsList);
    }


    @Test(description = "Verify if Create Job Api is creating inwarranty job", groups = {"api", "regression", "smoke"})
    public void createJobAPITTest() {
        jobService.createJob(Role.FD, createJobPayload)
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
