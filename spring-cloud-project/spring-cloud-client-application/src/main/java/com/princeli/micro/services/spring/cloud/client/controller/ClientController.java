package com.princeli.micro.services.spring.cloud.client.controller;

import com.princeli.micro.services.spring.cloud.client.annotation.CustomizedLoadBalanced;
import com.princeli.micro.services.spring.cloud.client.loadbalance.LoadBalancedRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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
    @CustomizedLoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    @LoadBalanced
    private RestTemplate lbRestTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String currentServiceName;


    //线程安全
    private volatile Set<String> targetUrls = new HashSet<>();

//    private volatile Map<String,Set<String>> targetUrlsCache = new HashMap<>();
//
//    @Scheduled(fixedRate =  10 * 1000)
//    public void updateTargetUrlsCache(){
//        Map<String,Set<String>> oldTargetUrlsCache = this.targetUrlsCache;
//        Map<String,Set<String>> newTargetUrlsCache = new HashMap<>();
//
//        discoveryClient.getServices().forEach(serviceName ->{
//
//            //获取当前应用所有列表
//            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
//            Set<String> newTargetUrls = serviceInstances
//                    .stream()
//                    .map(s->
//                            s.isSecure() ?
//                                    "https://"+s.getHost()+":"+s.getPort() :
//                                    "http://"+s.getHost()+":"+s.getPort()
//                    ).collect(Collectors.toSet());
//            newTargetUrlsCache.put(serviceName,newTargetUrls);
//        });
//
//        this.targetUrlsCache = newTargetUrlsCache;
//
//    }


    @GetMapping("/invoke/{serviceName}/say")
    public String invokeSay(@PathVariable String serviceName, @RequestParam String message){
        //发送请求
        return restTemplate.getForObject("/"+serviceName+"/say?message="+message,String.class);

    }

    @GetMapping("/lb/invoke/{serviceName}/say")
    public String lbInvokeSay(@PathVariable String serviceName, @RequestParam String message){
        //发送请求
        return lbRestTemplate.getForObject("http://"+serviceName+"/say?message="+message,String.class);

    }

    @Bean
    public ClientHttpRequestInterceptor interceptor(){
        return new LoadBalancedRequestInterceptor();
    }

    //Ribbon RestTemplate bean
    @LoadBalanced
    @Bean
    public RestTemplate loadBalancedRestTemplate(){
        return new RestTemplate();
    }

    //自定义RestTemplate bean
    @Bean
    @Autowired
    @CustomizedLoadBalanced
    public RestTemplate restTemplate(){//依赖注入
        return new RestTemplate();
    }

    @Bean
    @Autowired
    public Object customizer(@CustomizedLoadBalanced Collection<RestTemplate> restTemplates,ClientHttpRequestInterceptor interceptor){
        restTemplates.forEach(r->{
            r.setInterceptors(Arrays.asList(interceptor));
        });
        return new Object();
    }



//    @Scheduled(fixedRate =  10 * 1000)
//    public void updateInvocationUrls(){
//        Set<String> oldTargetUrls = this.targetUrls;
//
//        //获取当前应用所有列表
//        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(currentServiceName);
//        Set<String> newTargetUrls = serviceInstances
//                .stream()
//                .map(s->
//                        s.isSecure() ?
//                                "https://"+s.getHost()+":"+s.getPort() :
//                                "http://"+s.getHost()+":"+s.getPort()
//                ).collect(Collectors.toSet());
//
//        this.targetUrls = newTargetUrls;
//        oldTargetUrls.clear();
//
//    }

//    @GetMapping("/invoke/say")
//    public String invokeSay(@RequestParam String message){
//        //服务器列表快照
//        List<String> targetUrls = new ArrayList<>(this.targetUrls);
//
//        int size = targetUrls.size();
//
//        int index = new Random().nextInt(size);
//        //选择其中一台服务器
//        String targetUrl = targetUrls.get(index);
//
//        //发送请求
//        return restTemplate.getForObject(targetUrl+"/say?message="+message,String.class);
//
//    }

}
