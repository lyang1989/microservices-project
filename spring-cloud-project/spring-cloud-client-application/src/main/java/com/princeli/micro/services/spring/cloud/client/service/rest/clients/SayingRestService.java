package com.princeli.micro.services.spring.cloud.client.service.rest.clients;

import com.princeli.micro.services.spring.cloud.client.annotation.RestClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.stream.Stream;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-08 09:37
 **/
@RestClient(name = "${saying.rest.service.name}")
public interface SayingRestService {

    @GetMapping("/say")
    String say(@RequestParam String message);


    public static void main(String[] args) throws Exception {
        Method method = SayingRestService.class.getMethod("say",String.class);
        Parameter parameter = method.getParameters()[0];
        System.out.println(parameter);

        parameter.isNamePresent();

        //Method method = ReflectionUtils.findMethod(SayingRestService.class,"say",String.class);
        ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        Stream.of(discoverer.getParameterNames(method)).forEach(System.out::println);
    }

}

