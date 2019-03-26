package com.princeli.micro.services.spring.cloud.server.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-21 15:42
 **/
public interface SimpleMessageReceiver {

    @Input("gupao2018")
    SubscribableChannel gupao();

}
