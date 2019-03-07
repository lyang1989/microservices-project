package com.princeli.micro.services.spring.cloud.server.web.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-06 11:55
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CircuitBreakerHandlerInterceptor());
    }
}
