package com.princeli.micro.services.spring.cloud.client.annotation;

import java.lang.annotation.*;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-11 09:36
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestClient {

    /**
     * REST服务应用名称
     * @return
     */
    String name();

}
