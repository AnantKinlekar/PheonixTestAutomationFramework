package com.api.tests;
import static io.restassured.RestAssured.*;
import static com.api.constant.Role.*;
import com.api.pojo.*;
import static com.api.utils.SpecUtil.*;
import org.testng.annotations.Test;


public class CreateJobAPITest {

    @Test
    public void createJobAPITTest() {
        Customer customer = new Customer("Anant", "Kinlekar", "7995924124", "", "anantkinlekar18@gmail.com", "");
        CustomerAddress customerAddress = new CustomerAddress("602", "Vasavi Arcade", "Munneshwar Temple Road", "ECC Road", "Paatandur Agrahara", "416410", "India", "Maharashtra");
        CustomerProduct customerProduct = new CustomerProduct("2025-04-06T18:30:00.000Z", "15785930780289", "15785930780289", "15785930780289", "2025-04-06T18:30:00.000Z", 1, 1);
        Problems problems = new Problems(1, "Battery issue");
        Problems[] problemsArray = new Problems[1];
        problemsArray[0] = problems;

        CreateJobPayload createJobPayload = new CreateJobPayload(
                0,
                2,
                1,
                1,
                customer,
                customerAddress,
                customerProduct,
                problemsArray);

        given()
                .spec(requestSpecWithAuth(FD, createJobPayload))
                .when()
                .post("/job/create")
                .then()
                .spec(responseSpec_OK());

    }
}
