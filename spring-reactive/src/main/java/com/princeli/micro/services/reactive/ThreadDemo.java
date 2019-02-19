package com.princeli.micro.services.reactive;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-02-19 09:43
 **/
public class ThreadDemo {

    public static void main(String[] args) throws InterruptedException {
        println("Hello,World 1");

        //AtomicBoolean done = new AtomicBoolean(false);

        Thread thread = new Thread(()->{
            println("Hello,World 2018");

            //done.set(true);
        });


        thread.setName("sub-thread");
        thread.start();

        thread.join();  //等待线程销毁


        println("Hello,World 2");
    }



    public static void println(String message) {
        System.out.printf("[线程:%s] message:%s\n",Thread.currentThread().getName(),message);
    }
}
