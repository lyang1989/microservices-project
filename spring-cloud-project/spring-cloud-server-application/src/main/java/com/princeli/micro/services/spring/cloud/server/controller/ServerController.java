package com.princeli.micro.services.spring.cloud.server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-04 09:31
 **/
@RestController
public class ServerController {

    @Value("${spring.application.name}")
    private String currentServiceName;


    @GetMapping("/say")
    public String say(@RequestParam String message){
        System.out.println("ServerController 接收到消息 - say:" + message);
        return "hello,"+message;

    }
}
