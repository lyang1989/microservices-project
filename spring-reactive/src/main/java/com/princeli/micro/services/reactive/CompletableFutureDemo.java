package com.princeli.micro.services.reactive;

import java.util.concurrent.CompletableFuture;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-02-19 09:15
 **/
public class CompletableFutureDemo {
    public static void main(String[] args) {
        println("当前线程");

        CompletableFuture.supplyAsync(()->{
            println("第一步返回\"Hello\"");
           return "Hello";
        }).thenApplyAsync(result->{
            println("第二步在第一步结果+\",World\"");
            return result+",World";
        }).thenAccept(CompletableFutureDemo::println)
                .whenComplete((v,error)->{
                    println("执行结束!");
                })
                .join()
        ;

    }


    public static void println(String message) {
        System.out.printf("[线程:%s] message:%s\n",Thread.currentThread().getName(),message);
    }
}
