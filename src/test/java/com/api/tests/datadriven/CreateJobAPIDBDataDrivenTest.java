package com.api.tests.datadriven;

import static com.api.constant.Role.*;
import com.api.request.model.CreateJobPayload;
import com.api.services.JobService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

@Listeners(com.listeners.APITestListener.class)
public class CreateJobAPIDBDataDrivenTest {
    private JobService jobService;

    @BeforeMethod(description = "Initializing Job Service")
    public void setup() {
        jobService = new JobService();
    }

    @Test(description = "Verify if Create Job Api is creating inwarranty job", groups = {"api", "regression", "datadriven", "csv"}, dataProviderClass = com.dataproviders.DataProviderUtils.class, dataProvider = "createJobAPIDBDataProvider")
    public void createJobAPITTest(CreateJobPayload createJobPayload) {

        jobService.createJob(FD, createJobPayload)
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
