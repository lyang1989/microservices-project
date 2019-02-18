package com.princeli.micro.services.mvc.service;

import com.princeli.micro.services.mvc.annotation.TransactionalService;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-02-18 11:09
 **/
@TransactionalService(value="echoService-2018",txName = "myTxName")

public class EchoService {

    public void echo(String message){
        System.out.println(message);
    }
}
