package com.api.utils;

import static com.api.utils.ConfigManager.*;
import static com.api.utils.AuthTokenProvider.*;
import com.api.constant.Role;
import com.api.filters.SensitiveDataFilter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
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
                .addFilter(new SensitiveDataFilter())
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
                .addFilter(new SensitiveDataFilter())
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
                .addFilter(new SensitiveDataFilter())
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
                .addFilter(new SensitiveDataFilter())
                .build();

        return request;
    }

    public static ResponseSpecification responseSpec_OK(){
        ResponseSpecification response =new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectResponseTime(lessThan(1000L))
                .build();

        return response;
    }

    public static ResponseSpecification responseSpec_JSON(int statusCode){
        ResponseSpecification response =new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(statusCode)
                .expectResponseTime(lessThan(1000L))
                .build();

        return response;
    }

    //NOT CHECKING CONTENT TYPE IN THIS METHOD
    public static ResponseSpecification responseSpec_TEXT(int statusCode){
        ResponseSpecification response =new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectResponseTime(lessThan(1000L))
                .build();

        return response;
    }
}
