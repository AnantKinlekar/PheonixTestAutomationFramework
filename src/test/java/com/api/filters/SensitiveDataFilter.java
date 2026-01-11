package com.api.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class SensitiveDataFilter implements Filter {
    public Response filter(FilterableRequestSpecification var1, FilterableResponseSpecification var2, FilterContext var3){
        System.out.println("__________________________________________ Hello From the Filtered Request __________________________________________");
        Response response = var3.next(var1, var2);
        System.out.println("__________________________________________ Hello From the Filtered Response __________________________________________");
        return response;
    }
}
