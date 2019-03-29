package com.princeli.micro.services.spring.cloud.server.controller;

import org.springframework.context.*;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: microservices-project
 * @description: 远程事件接收器 控制器
 * @author: ly
 * @create: 2019-03-28 14:50
 **/
@RestController
public class RemoteAppEventReceiverController implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;



    @PostMapping("/receive/remote/event/")
    public String receive(@RequestBody Map<String,Object> data){

        //事件的发送者
        String sender = (String) data.get("sender");
        //事件的数据内容
        Object value  =  data.get("value");
        //事件类型
        String type = (String) data.get("type");

        //接收到对象内容，同样也要发送事件到本地，做处理

        System.out.println(sender+"--"+value+"--"+type);

        publisher.publishEvent(new SenderRemoteAppEvent(sender,value));

        return "received";

    }


    private static class SenderRemoteAppEvent extends ApplicationEvent {

        private final String sender;

        private SenderRemoteAppEvent(String sender,Object value){
            super(value);
            this.sender=sender;
        }


        public String getSender() {
            return sender;
        }
    }

    @Async
    @EventListener
    public void onEvent(SenderRemoteAppEvent event){
        System.out.println("接收到事件源:" + event +"来自应用:" +event.getSender());

    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }


}
