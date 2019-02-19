package com.princeli.micro.services.reactive;

import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.GenericApplicationContext;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-02-18 16:25
 **/
public class SpringEventDemo {
    public static void main(String[] args) {


        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();


        ExecutorService executorService = Executors.newSingleThreadExecutor();

        multicaster.setTaskExecutor(executorService);

        multicaster.addApplicationListener(event -> {
            System.out.printf("[线程:%s] event:%s\n",Thread.currentThread().getName(),event);
        });


        multicaster.multicastEvent(new PayloadApplicationEvent("Hello,World","Hello,World"));


        executorService.shutdown();

//        GenericApplicationContext context = new GenericApplicationContext();
//
//        context.addApplicationListener(event -> {
//            System.out.printf("[线程:%s] event:%s\n",Thread.currentThread().getName(),event);
//        });
//
//        context.refresh();
//
//        context.publishEvent("Hello,World");
//
//        context.close();


    }

}
