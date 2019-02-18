package com.princeli.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-01-23 15:55
 **/
@Configuration
public class SpringAnnotationDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringAnnotationDemo.class);
        context.refresh();


        System.out.println(context.getBean(SpringAnnotationDemo.class));


    }
}
