package com.princeli.micro.services.reactive;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.stream.Stream;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-02-19 11:35
 **/
public class ReactorDemo {
    public static void main(String[] args) throws InterruptedException {
        Flux.just(0,1,2,3,4,5,6,7,8,9)
                .filter(v ->v%2 == 1)  //获取奇数
                .map(v -> v-1)         //奇数变偶数
                .reduce(Integer::sum)  //聚合操作
                .subscribeOn(Schedulers.parallel())
                //.block();
                .subscribe(ReactorDemo::println) //订阅才执行
        ;
        Thread.sleep(1000);


        Stream.of(1,2,3,4,5,6)          //生产
                .map(String::valueOf)   //处理
                .forEach(System.out::println);             //消费
    }



    public static void println(Object message) {
        System.out.printf("[线程:%s] message:%s\n",Thread.currentThread().getName(),message);
    }
}
