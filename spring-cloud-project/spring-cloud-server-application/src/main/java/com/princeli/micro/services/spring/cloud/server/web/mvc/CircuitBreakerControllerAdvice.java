package com.princeli.micro.services.spring.cloud.server.web.mvc;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.TimeoutException;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-06 13:27
 **/
@RestControllerAdvice
public class CircuitBreakerControllerAdvice {

    @ExceptionHandler
    public void onTimeoutException(TimeoutException timeoutException, Writer writer) throws IOException {
        writer.write(errorContent(""));
        writer.flush();
        writer.close();
    }


    public String errorContent(String message) {
        return "Fault";
    }
}


