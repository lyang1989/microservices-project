package com.princeli.micro.services.spring.cloud.client.service.rest.clients;

import com.princeli.micro.services.spring.cloud.client.annotation.RestClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-08 09:37
 **/
@RestClient(name = "${saying.rest.service.name}")
public interface SayingRestService {

    @GetMapping("/say")
    String say(@RequestParam("message") String message);

}
