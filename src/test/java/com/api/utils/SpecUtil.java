package com.api.utils;

import static com.api.utils.ConfigManager.*;
import static com.api.utils.AuthTokenProvider.*;
import com.api.constant.Role;
import com.api.filters.SensitiveDataFilter;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.Matchers.*;

public class SpecUtil {

    @Step("Setting up the BASE URI, Content Type as Application/JSON and attaching senitiveDataFilter")
    public static RequestSpecification requestSpec(){

        RequestSpecification request =new RequestSpecBuilder()
                .setBaseUri(getProperty("BASE_URI"))
                .and()
                .setContentType(ContentType.JSON)
                .and()
                .setAccept(ContentType.JSON)
                .addFilter(new SensitiveDataFilter())
                .addFilter(new AllureRestAssured())
                .build();

        return request;

    }

    @Step("Setting up the BASE URI, Content Type as Application/JSON with Payload and attaching senitiveDataFilter")
    public static RequestSpecification requestSpec(Object payload){

        RequestSpecification request =new RequestSpecBuilder()
                .setBaseUri(getProperty("BASE_URI"))
                .and()
                .setContentType(ContentType.JSON)
                .and()
                .setAccept(ContentType.JSON)
                .and()
                .setBody(payload)
                .addFilter(new SensitiveDataFilter())
                .addFilter(new AllureRestAssured())
                .build();

        return request;

    }

    @Step("Setting up the BASE URI, Content Type as Application/JSON with ROLE and attaching senitiveDataFilter")
    public static RequestSpecification requestSpecWithAuth(Role role){
        RequestSpecification request =new RequestSpecBuilder()
                .setBaseUri(getProperty("BASE_URI"))
                .and()
                .setContentType(ContentType.JSON)
                .and()
                .setAccept(ContentType.JSON)
                .and()
                .addHeader("Authorization", getToken(role))
                .addFilter(new SensitiveDataFilter())
                .addFilter(new AllureRestAssured())
                .build();

        return request;
    }

    @Step("Setting up the BASE URI, Content Type as Application/JSON with ROLE and Payload and attaching senitiveDataFilter")
    public static RequestSpecification requestSpecWithAuth(Role role, Object payload){
        RequestSpecification request =new RequestSpecBuilder()
                .setBaseUri(getProperty("BASE_URI"))
                .and()
                .setContentType(ContentType.JSON)
                .and()
                .setAccept(ContentType.JSON)
                .and()
                .addHeader("Authorization", getToken(role))
                .setBody(payload)
                .addFilter(new SensitiveDataFilter())
                .addFilter(new AllureRestAssured())
                .build();

        return request;
    }

    @Step("Expecting the response to have Content Type as Application/JSON, Status 200 and Response Time less than 1000ms")
    public static ResponseSpecification responseSpec_OK(){
        ResponseSpecification response =new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectResponseTime(lessThan(1000L))
                .build();

        return response;
    }

    @Step("Expecting the response to have Content Type as Application/JSON, Response Time less than 1000ms and Status Code")
    public static ResponseSpecification responseSpec_JSON(int statusCode){
        ResponseSpecification response =new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(statusCode)
                .expectResponseTime(lessThan(1000L))
                .build();

        return response;
    }

    @Step("Expecting the response to have Content Type as TEXT, Response Time less than 1000ms and status code")
    public static ResponseSpecification responseSpec_TEXT(int statusCode){
        ResponseSpecification response =new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectResponseTime(lessThan(1000L))
                .build();

        return response;
    }
}
