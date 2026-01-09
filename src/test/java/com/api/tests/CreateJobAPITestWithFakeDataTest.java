package com.api.tests;

import com.api.constant.Role;
import com.api.request.model.*;
import com.api.services.JobService;
import com.api.utils.FakerDataGenerator;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.JobHeadDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.JobHeadModel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;


public class CreateJobAPITestWithFakeDataTest {
    private CreateJobPayload createJobPayload;
    private Customer customer;
    private CustomerAddress customerAddress;
    private CustomerProduct customerProduct;
    private Problems problems;
    private List<Problems> problemsList;
    private static final String COUNTRY = "India";
    private JobService jobService;


    @BeforeMethod(description = "Creating Create Job Api request Payload and Initializing Job Service")
    public void setup() {
        createJobPayload = FakerDataGenerator.generateFakeCreateJobData();
        jobService = new JobService();
    }


    @Test(description = "Verify if Create Job Api is creating inwarranty job", groups = {"api", "regression", "smoke"})
    public void createJobAPITTest() {

        int customerId = jobService.createJob(Role.FD, createJobPayload)
                .then()
                .spec(responseSpec_OK())
                .body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
                .body("message", equalTo("Job created successfully. "))
                .body("data.mst_service_location_id", equalTo(1))
                .body("data.mst_platform_id", equalTo(2))
                .body("data.mst_warrenty_status_id", equalTo(1))
                .body("data.mst_oem_id", equalTo(1))
                .body("data.job_number", startsWith("JOB_"))
                .extract().body().jsonPath().getInt("data.tr_customer_id");

        Customer expectedCustomerData = createJobPayload.customer();
        CustomerDBModel actualCustomerDataInDB = CustomerDao.getCustomerInfo(customerId);

        Assert.assertEquals(actualCustomerDataInDB.getFirst_name(), expectedCustomerData.first_name());
        Assert.assertEquals(actualCustomerDataInDB.getLast_name(), expectedCustomerData.last_name());
        Assert.assertEquals(actualCustomerDataInDB.getMobile_number(), expectedCustomerData.mobile_number());
        Assert.assertEquals(actualCustomerDataInDB.getMobile_number_alt(), expectedCustomerData.mobile_number_alt());
        Assert.assertEquals(actualCustomerDataInDB.getEmail_id(), expectedCustomerData.email_id());
        Assert.assertEquals(actualCustomerDataInDB.getEmail_id_alt(), expectedCustomerData.email_id_alt());


        CustomerAddressDBModel customerAddressFromDb = CustomerAddressDao.getCustomerAddress(actualCustomerDataInDB.getTr_customer_address_id());

        Assert.assertEquals(customerAddressFromDb.getFlat_number(), createJobPayload.customer_address().flat_number());
        Assert.assertEquals(customerAddressFromDb.getApartment_name(), createJobPayload.customer_address().apartment_name());
        Assert.assertEquals(customerAddressFromDb.getArea(), createJobPayload.customer_address().area());
        Assert.assertEquals(customerAddressFromDb.getLandmark(), createJobPayload.customer_address().landmark());
        Assert.assertEquals(customerAddressFromDb.getState(), createJobPayload.customer_address().state());
        Assert.assertEquals(customerAddressFromDb.getStreet_name(), createJobPayload.customer_address().street_name());
        Assert.assertEquals(customerAddressFromDb.getCountry(), createJobPayload.customer_address().country());
        Assert.assertEquals(customerAddressFromDb.getPincode(), createJobPayload.customer_address().pincode());

        JobHeadModel jobHeadDataFromDB = JobHeadDao.getJobHeadInfo(customerId);

        Assert.assertEquals(jobHeadDataFromDB.getMst_oem_id(), createJobPayload.mst_oem_id());
        Assert.assertEquals(jobHeadDataFromDB.getMst_service_location_id(), createJobPayload.mst_service_location_id());
        Assert.assertEquals(jobHeadDataFromDB.getMst_warrenty_status_id(), createJobPayload.mst_warrenty_status_id());
        Assert.assertEquals(jobHeadDataFromDB.getMst_platform_id(), createJobPayload.mst_platform_id());


    }
}
