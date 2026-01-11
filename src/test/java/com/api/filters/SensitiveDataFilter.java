package com.api.filters;

import com.api.services.AuthService;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SensitiveDataFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(SensitiveDataFilter.class);

    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext var3) {
        redactPayload(requestSpec);
        Response response = var3.next(requestSpec, responseSpec);
        redactResponseBody(response);
        return response;
    }

    //create a method which will redact the password
    public void redactPayload(FilterableRequestSpecification requestSpec) {
        String requestPayload = requestSpec.getBody().toString();
        //Journey to hide payload starts
        requestPayload = requestPayload.replaceAll("\"password\"\s*:\s*\"[^\"]+\"", "\"password\": \"[REDACTED]\"");
        LOGGER.info("REQUEST PAYLOAD: {}", requestPayload);
    }

    public void redactResponseBody(Response response) {
        String responoseBody = response.asPrettyString();
        responoseBody = responoseBody.replaceAll("\"token\"\s*:\s*\"[^\"]+\"", "\"token\": \"[REDACTED]\"");
        LOGGER.info("RESPONSE BODY : {}", responoseBody);

    }
}
