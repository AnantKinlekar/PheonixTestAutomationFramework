package com.api.utils;

import com.api.request.model.*;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FakerDemo2 {
    private static final String COUNTRY = "India";
    public static void main(String[] args) {
       //I want to create a fake customer

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
        System.out.println(customerAddress);

        String dop = DateTimeUtil.getTimeWithDaysAgo(10);
        String imeiSerialNumber = faker.numerify("###############");
        String popUrl = faker.internet().url();

        CustomerProduct customerProduct = new CustomerProduct(dop, imeiSerialNumber ,imeiSerialNumber, imeiSerialNumber, popUrl, 1, 1);
        System.out.println(customerProduct);


        //I want to generate a random number from 1 to 26 for problems
        Random random = new Random();
        int randomId = random.nextInt(1, 27);
        String fakeRemark = faker.lorem().sentence(3);
        Problems problems = new Problems(3, fakeRemark);
        System.out.println(problems);

        List<Problems> problemsList = new ArrayList<>();
        problemsList.add(problems);

        CreateJobPayload creaetJobPayload = new CreateJobPayload(0,2,1,1, customer, customerAddress, customerProduct, problemsList);
        System.out.println(creaetJobPayload);
    }
}
