package com.princeli.micro.services.spring.cloud.server.annotation;

import java.lang.annotation.*;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-07 08:46
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TimeoutCircuitBreaker {

    /**
     * 超时时间
     * @return
     */
    long timeout();
}
