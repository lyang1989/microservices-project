package com.princeli.micro.services.reactive.webflux;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-02-19 11:58
 **/
@RestController
public class WebFluxController {

    @RequestMapping("")
    public Mono<String> index(){
        println("执行计算");

        return Mono.fromSupplier(() ->{

            println("返回结果");

            return "Hello,World";
        });
    }

    public static void println(String message) {
        System.out.printf("[线程:%s] message:%s\n",Thread.currentThread().getName(),message);
    }

}
