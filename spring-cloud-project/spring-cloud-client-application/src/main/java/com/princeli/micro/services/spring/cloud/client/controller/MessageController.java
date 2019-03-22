package com.princeli.micro.services.spring.cloud.client.controller;

import com.princeli.micro.services.spring.cloud.client.stream.SimpleMessageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-18 11:41
 **/
@RestController
public class MessageController {


    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Autowired
//    private Source source;

    @Autowired
    private SimpleMessageService simpleMessageService;



    @GetMapping
    private String send(@RequestParam String message){
        rabbitTemplate.convertAndSend("Hello,world");
        return "OK";
    }


    @GetMapping("/stream/send")
    public boolean streamSend(@RequestParam String message){
       MessageChannel messageChannel = simpleMessageService.gupao();
       return messageChannel.send(new GenericMessage<String>("Hello,Word"));
    }
}
