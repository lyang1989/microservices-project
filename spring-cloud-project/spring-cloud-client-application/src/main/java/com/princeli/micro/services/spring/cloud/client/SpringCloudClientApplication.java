package com.princeli.micro.services.spring.cloud.client;

import com.princeli.micro.services.spring.cloud.client.annotation.EnableRestClient;
import com.princeli.micro.services.spring.cloud.client.event.HttpRemoteAppEventListener;
import com.princeli.micro.services.spring.cloud.client.service.feign.clients.SayingService;
import com.princeli.micro.services.spring.cloud.client.service.rest.clients.SayingRestService;
import com.princeli.micro.services.spring.cloud.client.stream.SimpleMessageService;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
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
@EnableFeignClients(clients = SayingService.class)     //引入FeignClient
@EnableRestClient(clients = SayingRestService.class)   //引入RestClient
@EnableBinding(SimpleMessageService.class)
public class SpringCloudClientApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .listeners(new HttpRemoteAppEventListener())
                .run(args);
    }
}
