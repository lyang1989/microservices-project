package com.princeli.micro.services.reactive;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-18 10:44
 **/
public class UncaughtExceptionHandlerDemo {
    public static void main(String[] args) {

        Thread.currentThread().setUncaughtExceptionHandler((t,e)->{
            System.out.println(e.getMessage());
        });

        throw new RuntimeException("故意的");



    }
}
