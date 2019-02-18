package com.princeli.micro.services.mvc.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-02-18 11:02
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
@Transactional
public @interface TransactionalService {

    @AliasFor(annotation = Service.class)
    String value() default "";

    @AliasFor(annotation = Transactional.class,attribute = "value")
    String txName() default "";

}
