package com.princeli.micro.services.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-02-25 10:01
 **/
@EnableAutoConfiguration
@RestController
public class SpringBootApplicationBootstrap {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext();
        parentContext.setId("小马哥");
        //在小马哥下注册helloWorld String类型的bean
        parentContext.registerBean("helloWorld",String.class,"Hello,World");

        parentContext.refresh();

        new SpringApplicationBuilder(SpringBootApplicationBootstrap.class)
                .parent(parentContext)  //显式的设置双亲上下文
                .run(args);

//        SpringApplication.run(SpringBootApplicationBootstrap.class);
    }

    @Autowired
    @Qualifier("helloWorld")
    private String message;

    @RequestMapping("")
    public String index(){
        return message;
    }


}
