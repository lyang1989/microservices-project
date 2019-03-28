package com.princeli.micro.services.spring.cloud.client.controller;

import com.princeli.micro.services.spring.cloud.client.event.RemoteAppEvent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.*;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @program: microservices-project
 * @description: 远程事件控制器
 * @author: ly
 * @create: 2019-03-27 15:46
 **/
@RestController
public class RemoteAppEventSenderController implements
        ApplicationEventPublisherAware{

    private ApplicationEventPublisher publisher;

    @Autowired
    private DiscoveryClient discoveryClient;


    @GetMapping("/send/remote/event")
    public String sendEvent(@RequestParam String message){
        publisher.publishEvent(message);
        return "Sent";
    }


    @PostMapping("/send/remote/event/{appName}")
    public String sendAppCluster(@PathVariable String appName, @RequestBody Object data){
        //发送到自己
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(appName);
        RemoteAppEvent remoteAppEvent = new RemoteAppEvent(data,appName,true);
        //发送事件给当前上下文
        publisher.publishEvent(remoteAppEvent);
        return "Ok";
    }



//    @PostMapping("/send/remote/event/{appName}")
//    public String sendAppCluster(@PathVariable String appName, @RequestParam String message){
//        //发送到自己
//        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(appName);
//        RemoteAppEvent remoteAppEvent = new RemoteAppEvent(message,currentAppName, appName,serviceInstances);
//        //发送事件给当前上下文
//        publisher.publishEvent(remoteAppEvent);
//        return "Ok";
//    }

    @PostMapping("/send/remote/event/{appName}/{ip}/{port}")
    public String sendAppCluster(@PathVariable String appName,
                                 @PathVariable String ip,
                                 @PathVariable int port,
                                 @RequestBody Object data){

        ServiceInstance serviceInstance = new DefaultServiceInstance(appName,ip,port,false);
        RemoteAppEvent remoteAppEvent = new RemoteAppEvent(data, appName,false);
        //发送事件给当前上下文
        publisher.publishEvent(remoteAppEvent);
        return "Ok";
    }


    @EventListener
    public void onMessage(PayloadApplicationEvent event){
        System.out.println("接收事件:" + event.getPayload());

    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

}
