package com.api.tests;

import com.api.constant.*;
import com.api.request.model.*;
import com.api.utils.DateTimeUtil;
import com.github.javafaker.Faker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.api.constant.Role.FD;
import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;


public class CreateJobAPITest2 {
    private CreateJobPayload createJobPayload;
    private Customer customer;
    private CustomerAddress customerAddress;
    private CustomerProduct customerProduct;
    private Problems problems;
    private List<Problems> problemsList;
    private static final String COUNTRY = "India";



    @BeforeMethod(description = "Creating Create Job Api request Payload")
    public void setup() {
        Faker faker = new Faker(new Locale("en-IND"));
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String mobileNo = faker.numerify("799#######");
        String alternateMobileNumber = faker.numerify("979#######");
        String emailAddress = faker.internet().emailAddress();

        Customer customer = new Customer(firstName, lastName,mobileNo,alternateMobileNumber,emailAddress,"");
        System.out.println(customer);

        String flat_number = faker.address().buildingNumber();
        String apartmentName = faker.address().streetName();
        String streetName = faker.address().streetName();
        String landmark = faker.address().buildingNumber();
        String area = faker.address().streetName();
        String pincode = faker.numerify("######");
        String state = faker.address().state();

        CustomerAddress customerAddress = new CustomerAddress(flat_number, apartmentName, streetName, landmark,area, pincode, COUNTRY, state);

        String dop = DateTimeUtil.getTimeWithDaysAgo(10);
        String imeiSerialNumber = faker.numerify("###############");
        String popUrl = faker.internet().url();

        CustomerProduct customerProduct = new CustomerProduct(dop, imeiSerialNumber ,imeiSerialNumber, imeiSerialNumber, popUrl, 1, 1);

        //I want to generate a random number from 1 to 26 for problems
        Random random = new Random();
        int randomId = random.nextInt(1, 27);
        String fakeRemark = faker.lorem().sentence(3);
        Problems problems = new Problems(randomId, fakeRemark);

        List<Problems> problemsList = new ArrayList<>();
        problemsList.add(problems);

        createJobPayload = new CreateJobPayload(0,2,1,1, customer, customerAddress, customerProduct, problemsList);
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
