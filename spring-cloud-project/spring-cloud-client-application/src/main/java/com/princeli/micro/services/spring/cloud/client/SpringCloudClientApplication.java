package com.princeli.micro.services.spring.cloud.client;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-01 14:25
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class SpringCloudClientApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
