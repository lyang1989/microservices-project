package com.princeli.micro.services.spring.cloud.ds.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-02-28 15:26
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class ZkDSClientApplication {

    public static void main(String[] args) {

        SpringApplication.run(ZkDSClientApplication.class,args);
    }
}
