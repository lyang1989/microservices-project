package com.princeli.micro.services.spring.cloud.server;

import com.princeli.micro.services.spring.cloud.server.aop.ServerControllerAspect;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-01 14:25
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringCloudServerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudServerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
