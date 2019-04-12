package com.princeli.micro.services.stream.binder.rocketmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-04-12 09:25
 **/
@Configuration
public class RockMQMessageChannelBinderConfiguration {


    @Bean
    public RockMQMessageChannelBinder rockMQMessageChannelBinder(){
        return new RockMQMessageChannelBinder();
    }

}
