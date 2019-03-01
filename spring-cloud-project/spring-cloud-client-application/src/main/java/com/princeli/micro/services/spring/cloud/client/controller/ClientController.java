package com.princeli.micro.services.spring.cloud.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-01 14:27
 **/
@RestController
public class ClientController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String currentServiceName;


    //线程安全
    private volatile Set<String> targetUrls = new HashSet<>();

    @Scheduled(fixedRate =  10 * 1000)
    public void updateInvocationUrls(){
        Set<String> oldTargetUrls = this.targetUrls;

        //获取当前应用所有列表
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(currentServiceName);
        Set<String> newTargetUrls = serviceInstances
                .stream()
                .map(s->
                    s.isSecure() ?
                            "https://"+s.getHost()+":"+s.getPort() :
                            "http://"+s.getHost()+":"+s.getPort()
                ).collect(Collectors.toSet());

        this.targetUrls = newTargetUrls;
        oldTargetUrls.clear();

    }


    @GetMapping("/invoke/say")
    public String invokeSay(@RequestParam String message){
        //服务器列表快照
        List<String> targetUrls = new ArrayList<>(this.targetUrls);

        int size = targetUrls.size();

        int index = new Random().nextInt(size);
        //选择其中一台服务器
        String targetUrl = targetUrls.get(index);

        //发送请求
        return restTemplate.getForObject(targetUrl+"/say?message="+message,String.class);

    }



    @GetMapping("/say")
    public String say(@RequestParam String message){
        System.out.println("接收到消息 - say:" + message);
        return "hello,"+message;

    }


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
