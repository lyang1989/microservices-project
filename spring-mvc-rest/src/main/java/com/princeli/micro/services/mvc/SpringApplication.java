package com.princeli.micro.services.mvc;

import com.princeli.micro.services.mvc.service.EchoService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.DefaultTransactionStatus;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-02-18 11:11
 **/
@ComponentScan(basePackages = "com.princeli.micro.services.mvc.service")
@EnableTransactionManagement
public class SpringApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(SpringApplication.class);

        context.refresh();

        context.getBeansOfType(EchoService.class).forEach((beanName,bean)->{
            System.out.println("Bean Name:"+beanName + ",Bean:"+bean);

            bean.echo("Hello,World");
        });


        context.close();

    }
}
