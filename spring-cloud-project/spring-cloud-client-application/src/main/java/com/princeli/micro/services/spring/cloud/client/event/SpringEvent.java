package com.princeli.micro.services.spring.cloud.client.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-27 15:04
 **/
public class SpringEvent {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.addApplicationListener( e ->{
            System.err.println("监听"+e.getClass().getSimpleName());
        });

        context.refresh();
        context.start();
        context.stop();
        context.close();

    }
}
