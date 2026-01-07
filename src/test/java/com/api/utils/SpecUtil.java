package com.api.utils;

import static com.api.utils.ConfigManager.*;
import static com.api.utils.AuthTokenProvider.*;
import com.api.constant.Role;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.Matchers.*;

public class SpecUtil {

    // GET and DEL
    public static RequestSpecification requestSpec(){

        RequestSpecification request =new RequestSpecBuilder()
                .setBaseUri(getProperty("BASE_URI"))
                .and()
                .setContentType(ContentType.JSON)
                .and()
                .setAccept(ContentType.JSON)
                .and()
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .log(LogDetail.HEADERS)
                .log(LogDetail.BODY)
                .build();

        return request;

    }

    public static RequestSpecification requestSpec(Object payload){

        RequestSpecification request =new RequestSpecBuilder()
                .setBaseUri(getProperty("BASE_URI"))
                .and()
                .setContentType(ContentType.JSON)
                .and()
                .setAccept(ContentType.JSON)
                .and()
                .setBody(payload)
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .log(LogDetail.HEADERS)
                .log(LogDetail.BODY)
                .build();

        return request;

    }

    public static RequestSpecification requestSpecWithAuth(Role role){
        RequestSpecification request =new RequestSpecBuilder()
                .setBaseUri(getProperty("BASE_URI"))
                .and()
                .setContentType(ContentType.JSON)
                .and()
                .setAccept(ContentType.JSON)
                .and()
                .addHeader("Authorization", getToken(role))
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .log(LogDetail.HEADERS)
                .log(LogDetail.BODY)
                .build();

        return request;
    }

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
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .log(LogDetail.HEADERS)
                .log(LogDetail.BODY)
                .build();

        return request;
    }

    public static ResponseSpecification responseSpec_OK(){
        ResponseSpecification response =new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectResponseTime(lessThan(1000L))
                .log(LogDetail.ALL)
                .build();

        return response;
    }

    public static ResponseSpecification responseSpec_JSON(int statusCode){
        ResponseSpecification response =new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(statusCode)
                .expectResponseTime(lessThan(1000L))
                .log(LogDetail.ALL)
                .build();

        return response;
    }

    //NOT CHECKING CONTENT TYPE IN THIS METHOD
    public static ResponseSpecification responseSpec_TEXT(int statusCode){
        ResponseSpecification response =new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectResponseTime(lessThan(1000L))
                .log(LogDetail.ALL)
                .build();

        return response;
    }
}
