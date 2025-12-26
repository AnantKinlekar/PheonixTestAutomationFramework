package com.api.utils;

import com.api.request.model.*;
import com.github.javafaker.Faker;

import java.util.*;

public class FakerDataGenerator {
    private static Faker faker = new Faker(new Locale("en-IND"));
    private static final String COUNTRY = "India";
    private static final int MST_SERVICE_LOCATION_ID = 0;
    private static final int MST_PLATFORM_ID = 2;
    private static final int MST_WARRENTY_STATUS_ID = 1;
    private static final int MST_OEM_ID = 1;
    private static final Random RANDOM = new Random();
    private static final int PRODUCT_ID = 1;
    private static final int MST_MODEL_ID = 1;


//    private static Customer customer;
//    private static CustomerAddress customerAddress;
//    private static CustomerProduct customerProduct;
//    private static List<Problems> problemsList;


    private FakerDataGenerator() {

    }

    public static CreateJobPayload generateFakeCreateJobData() {

        Customer customer = generateFakeCustomerData();
        CustomerAddress customerAddress = generateFakeCustomerAddressData();
        CustomerProduct customerProduct = generateFakeCustomerProductData();
        List<Problems> problemsList = generateFakeProblemsList();

        CreateJobPayload createJobPayload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLATFORM_ID, MST_WARRENTY_STATUS_ID, MST_OEM_ID,
                customer,
                customerAddress,
                customerProduct,
                problemsList);
        return createJobPayload;
    }

    public static Iterator<CreateJobPayload> generateFakeCreateJobData(int count) {
        List<CreateJobPayload> createJobPayloadList = new ArrayList<>();
        for(int i =1; i<=count;i++) {
            Customer customer = generateFakeCustomerData();
            CustomerAddress customerAddress = generateFakeCustomerAddressData();
            CustomerProduct customerProduct = generateFakeCustomerProductData();
            List<Problems> problemsList = generateFakeProblemsList();

            CreateJobPayload createJobPayload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLATFORM_ID, MST_WARRENTY_STATUS_ID, MST_OEM_ID,
                    customer,
                    customerAddress,
                    customerProduct,
                    problemsList);
            createJobPayloadList.add(createJobPayload);
        }
        return createJobPayloadList.iterator();
    }

    private static Customer generateFakeCustomerData() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String mobileNo = faker.numerify("799#######");
        String alternateMobileNumber = faker.numerify("979#######");
        String emailAddress = faker.internet().emailAddress();

        Customer customer = new Customer(firstName, lastName, mobileNo, alternateMobileNumber, emailAddress, "");
        return customer;
    }

    private static CustomerAddress generateFakeCustomerAddressData() {
        String flat_number = faker.address().buildingNumber();
        String apartmentName = faker.address().streetName();
        String streetName = faker.address().streetName();
        String landmark = faker.address().buildingNumber();
        String area = faker.address().streetName();
        String pincode = faker.numerify("######");
        String state = faker.address().state();

        CustomerAddress customerAddress = new CustomerAddress(flat_number, apartmentName, streetName, landmark, area, pincode, COUNTRY, state);
        return customerAddress;
    }

    private static CustomerProduct generateFakeCustomerProductData() {
        String dop = DateTimeUtil.getTimeWithDaysAgo(10);
        String imeiSerialNumber = faker.numerify("###############");
        String popUrl = faker.internet().url();

        CustomerProduct customerProduct = new CustomerProduct(dop, imeiSerialNumber, imeiSerialNumber, imeiSerialNumber, popUrl, PRODUCT_ID, MST_MODEL_ID);
        return customerProduct;
    }

    private static List<Problems> generateFakeProblemsList() {
        //I want to generate a RANDOM number from 1 to 26 for problems
        int randomId = RANDOM.nextInt(1, 27);
        String fakeRemark = faker.lorem().sentence(3);
        Problems problems = new Problems(randomId, fakeRemark);

        List<Problems> problemsList = new ArrayList<>();
        problemsList.add(problems);
        return problemsList;
    }

}
