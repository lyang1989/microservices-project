package com.princeli.micro.services.spring.cloud.server;

import com.princeli.micro.services.spring.cloud.server.aop.ServerControllerAspect;
import com.princeli.micro.services.spring.cloud.server.stream.SimpleMessageReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

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
@EnableBinding(SimpleMessageReceiver.class)
public class SpringCloudServerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudServerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }


    @Autowired
    private SimpleMessageReceiver simpleMessageReceiver;


    @PostConstruct
    public void init(){//接口编程
        SubscribableChannel subscribableChannel = simpleMessageReceiver.gupao();
        subscribableChannel.subscribe(message -> {
            MessageHeaders headers = message.getHeaders();
            String encoding = (String) headers.get("charset-encoding");
            String text = (String) headers.get("content-type");
            byte[] content = (byte[]) message.getPayload();

            try {
                System.out.println("接收到消息:"+new String(content,encoding));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        });
    }

    @StreamListener("gupao2018")
    public void onMessage(byte[] data){ //Spring Cloud Stream 注解编程
        System.out.println("onMessage(byte[])" + new String(data));

    }

    @StreamListener("gupao2018")
    public void onMessage(String data){ //注解编程
        System.out.println("onMessage(String)" + data);

    }

    @ServiceActivator(inputChannel = "gupao2018")
    public void onServiceActivator(String data){ // Spring Integration注解驱动
        System.out.println("onServiceActivator(String)" + data);
    }



}
