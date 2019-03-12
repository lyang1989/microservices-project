package com.princeli.micro.services.spring.cloud.client.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-11 09:34
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({RestClientsRegistrar.class})
public @interface EnableRestClient {

    Class<?>[] clients() default {};
}
