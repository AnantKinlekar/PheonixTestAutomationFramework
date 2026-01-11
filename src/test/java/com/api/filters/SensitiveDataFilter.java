package com.api.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class SensitiveDataFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(SensitiveDataFilter.class);

    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext var3) {
        LOGGER.info("----------------------------------------------- REQUEST DETAILS -----------------------------------------------");
        LOGGER.info("BASE URI: {}", requestSpec.getURI());
        LOGGER.info("HTTP METHOD : {}", requestSpec.getMethod());
        LOGGER.info("REQUEST HEADERS : \n{}", requestSpec.getHeaders());
        redactHeader(requestSpec);
        redactPayload(requestSpec);
        Response response = var3.next(requestSpec, responseSpec);
        LOGGER.info("----------------------------------------------- RESPONSE DETAILS -----------------------------------------------");
        LOGGER.info("STATUS CODE : {}", response.getStatusCode());
        LOGGER.info("STATUS CODE MESSAGE : {}", response.getStatusLine());
        LOGGER.info("RESPONSE TIME : {}ms", response.timeIn(TimeUnit.MILLISECONDS));
        LOGGER.info("RESPONSE HEADERS : \n{}", response.getHeaders());
        redactResponseBody(response);
        return response;
    }

    //create a method which will redact the password
    public void redactPayload(FilterableRequestSpecification requestSpec) {

        if (requestSpec.getBody() != null) {
            String requestPayload = requestSpec.getBody().toString();
            //Journey to hide payload starts
            requestPayload = requestPayload.replaceAll("\"password\"\s*:\s*\"[^\"]+\"", "\"password\": \"[REDACTED]\"");
            LOGGER.info("REQUEST PAYLOAD: \n{}", requestPayload);
        }

    }

    public void redactResponseBody(Response response) {
        String responoseBody = response.asPrettyString();
        responoseBody = responoseBody.replaceAll("\"token\"\s*:\s*\"[^\"]+\"", "\"token\": \"[REDACTED]\"");
        LOGGER.info("RESPONSE BODY : \n{}", responoseBody);

    }

    public void redactHeader(FilterableRequestSpecification requestSpec) {
       List<Header> headerList =  requestSpec.getHeaders().asList();
       for(Header h : headerList){
           if(h.getName().equalsIgnoreCase("Authorization")){
               LOGGER.info("HEADER {} : {}", h.getName(), "\"[REDACTED]\"");
           }else{
               LOGGER.info("HEADER {} : {}", h.getName(), h.getValue());
           }
       }
    }
}
