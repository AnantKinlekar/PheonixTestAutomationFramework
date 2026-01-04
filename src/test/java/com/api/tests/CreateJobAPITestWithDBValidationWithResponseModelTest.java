package com.api.tests;

import com.api.constant.*;
import com.api.request.model.*;
import com.api.response.model.CreateJobResponseModel;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.dao.MapJobProblemDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;
import com.database.model.MapJobProblemModel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.api.constant.Role.FD;
import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;


public class CreateJobAPITestWithDBValidationWithResponseModelTest {
    private CreateJobPayload createJobPayload;
    private Customer customer;
    private CustomerAddress customerAddress;
    private CustomerProduct customerProduct;
    private Problems problems;
    private List<Problems> problemsList;

    @BeforeMethod(description = "Creating Create Job Api request Payload")
    public void setup() {
        customer = new Customer("Anant", "Kinlekar", "7995924124", "", "anantkinlekar18@gmail.com", "");
        customerAddress = new CustomerAddress("602", "Vasavi Arcade", "Munneshwar Temple Road", "ECC Road", "Paatandur Agrahara", "416410", "India", "Maharashtra");
        customerProduct = new CustomerProduct(getTimeWithDaysAgo(1), "25892930887281", "25892930887281", "25892930887281", getTimeWithDaysAgo(1), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
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

        CreateJobResponseModel createJobResponseModel = given()
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
                .body("data.job_number", startsWith("JOB_"))
                .extract().as(CreateJobResponseModel.class);

        int customerId = createJobResponseModel.getData().getTr_customer_id();

        CustomerDBModel customerDataFromDB = CustomerDao.getCustomerInfo(customerId);
        Assert.assertEquals(customer.first_name(), customerDataFromDB.getFirst_name());
        Assert.assertEquals(customer.last_name(), customerDataFromDB.getLast_name());
        Assert.assertEquals(customer.mobile_number(), customerDataFromDB.getMobile_number());
        Assert.assertEquals(customer.mobile_number_alt(), customerDataFromDB.getMobile_number_alt());
        Assert.assertEquals(customer.email_id(), customerDataFromDB.getEmail_id());
        Assert.assertEquals(customer.email_id_alt(), customerDataFromDB.getEmail_id_alt());

        //now asserting customerAddress
        System.out.println("-------------------------------------------------------------------------");

        CustomerAddressDBModel customerAddressFromDb = CustomerAddressDao.getCustomerAddress(customerDataFromDB.getTr_customer_address_id());

        Assert.assertEquals(customerAddressFromDb.getFlat_number(), customerAddress.flat_number());
        Assert.assertEquals(customerAddressFromDb.getApartment_name(), customerAddress.apartment_name());
        Assert.assertEquals(customerAddressFromDb.getArea(), customerAddress.area());
        Assert.assertEquals(customerAddressFromDb.getLandmark(), customerAddress.landmark());
        Assert.assertEquals(customerAddressFromDb.getState(), customerAddress.state());
        Assert.assertEquals(customerAddressFromDb.getStreet_name(), customerAddress.street_name());
        Assert.assertEquals(customerAddressFromDb.getCountry(), customerAddress.country());
        Assert.assertEquals(customerAddressFromDb.getPincode(), customerAddress.pincode());

        System.out.println("-----------------------------------------------------------");
        //asserting problems
        int tr_job_head_id = createJobResponseModel.getData().getId();
        MapJobProblemModel jobDataFromDb = MapJobProblemDao.getProblemDetails(tr_job_head_id);
        Assert.assertEquals(jobDataFromDb.getMst_problem_id(), createJobPayload.problems().getFirst().id());
        Assert.assertEquals(jobDataFromDb.getRemark(), createJobPayload.problems().getFirst().remark());

        System.out.println("-----------------------------------------------------------");
        //asserting customer_product
        int productId = createJobResponseModel.getData().getTr_customer_product_id();

        CustomerProductDBModel customerProductDataFromDb = CustomerProductDao.getCustomerProductInfo(productId);
        Assert.assertEquals(customerProductDataFromDb.getDop(), customerProduct.dop());
        Assert.assertEquals(customerProductDataFromDb.getSerial_number(), customerProduct.serial_number());
        Assert.assertEquals(customerProductDataFromDb.getImei1(), customerProduct.imei1());
        Assert.assertEquals(customerProductDataFromDb.getImei2(), customerProduct.imei2());
        Assert.assertEquals(customerProductDataFromDb.getPopurl(), customerProduct.popurl());
        Assert.assertEquals(customerProductDataFromDb.getMst_model_id(), customerProduct.mst_model_id());

    }
}
