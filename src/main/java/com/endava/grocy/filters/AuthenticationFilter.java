package com.endava.grocy.filters;

import com.endava.grocy.util.EnvReader;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class AuthenticationFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext context) {
        requestSpec.header("GROCY-API-KEY", EnvReader.getAPIKey());
        return context.next(requestSpec, responseSpec);
    }
}
