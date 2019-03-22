package com.princeli.micro.services.spring.cloud.servlet.gateway;

import com.princeli.micro.services.spring.cloud.servlet.gateway.loadbalancer.ZookeeperLoadBalancer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-13 16:02
 **/
@SpringBootApplication
@EnableDiscoveryClient
@ServletComponentScan(basePackages = "com.princeli.micro.services.spring.cloud.servlet.gateway.servlet")
@EnableScheduling
public class SpringCloudServletGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudServletGateWayApplication.class);
    }

    @Bean
    public ZookeeperLoadBalancer zookeeperLoadBalancer(DiscoveryClient discoveryClient){
        return new ZookeeperLoadBalancer(discoveryClient);
    }
}
