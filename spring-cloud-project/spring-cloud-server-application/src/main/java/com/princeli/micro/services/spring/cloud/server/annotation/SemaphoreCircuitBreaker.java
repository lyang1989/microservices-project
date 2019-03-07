package com.princeli.micro.services.spring.cloud.server.annotation;

import java.lang.annotation.*;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-07 09:16
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SemaphoreCircuitBreaker {

    /**
     *信号量
     * @return
     */
    int value();
}
