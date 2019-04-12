package com.princeli.micro.services.spring.cloud.client.controller;

import com.princeli.micro.services.spring.cloud.client.stream.SimpleMessageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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

        Map<String,Object> headers = new HashMap<>();
        headers.put("charset-encoding","UTF-8");
        headers.put("content-type", MediaType.TEXT_PLAIN.toString());
        GenericMessage<String> msg = new GenericMessage<String>(message,headers);
       return messageChannel.send(msg);
    }


    @GetMapping("/stream/send/rocketmq")
    public boolean streamSendRocketMQ(@RequestParam String message){
        MessageChannel messageChannel = simpleMessageService.testChannel();
        return messageChannel.send(new GenericMessage<String>(message));
    }
}
