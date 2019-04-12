package com.princeli.micro.services.spring.cloud.client.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-20 14:16
 **/
public interface SimpleMessageService {

    @Output("gupao2018")      //Channel name
    MessageChannel gupao();   //destination=test2018


    @Output("test007")      //Channel name
    MessageChannel testChannel();//destination=test007

}


