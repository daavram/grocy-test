package com.endava.grocy.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.filter.log.LogDetail;
import io.restassured.internal.print.RequestPrinter;
import io.restassured.internal.print.ResponsePrinter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class LogFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(LogFilter.class);

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext context) {

        OutputStream
                outputStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(outputStream);

        //print request
        RequestPrinter.print(requestSpec, requestSpec.getMethod(), requestSpec.getURI(), LogDetail.ALL, requestSpec.getConfig().getLogConfig().blacklistedHeaders(), stream, true);

        //send request to server and get response
        Response response = context.next(requestSpec, responseSpec);

        //print response
        ResponsePrinter.print(response, response.body(), stream,
                LogDetail.ALL, true,
                requestSpec.getConfig().getLogConfig().blacklistedHeaders());

        //add an empty line
        try {
            outputStream.write("\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // print info to logger
        LOGGER.info(outputStream);

        return response;
    }
}
