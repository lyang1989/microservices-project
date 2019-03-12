package com.princeli.micro.services.spring.cloud.client.service.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-08 09:37
 **/
@FeignClient(name = "spring-cloud-server-application")
public interface SayingService {

    @GetMapping("/say")
    String say(@RequestParam String message);

}
