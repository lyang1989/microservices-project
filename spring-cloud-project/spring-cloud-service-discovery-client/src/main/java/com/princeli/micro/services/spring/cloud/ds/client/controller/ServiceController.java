package com.princeli.micro.services.spring.cloud.ds.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-01 09:23
 **/
@RestController
public class ServiceController {

    @Autowired
    private DiscoveryClient discoveryClient;


    /**
     * 返回所有服务名称
     * @return
     */
    @GetMapping("/services")
    public List<String> getAllServices(){
        return discoveryClient.getServices();
    }

    @GetMapping("/service/instances/{serviceName}")
    public List<String> getAllServiceInstances(@PathVariable String serviceName){
        return discoveryClient.getInstances(serviceName)
                .stream()
                .map(s->
                   s.getServiceId()+"-"+s.getHost()+":"+s.getPort()
                ).collect(Collectors.toList());
    }
}
