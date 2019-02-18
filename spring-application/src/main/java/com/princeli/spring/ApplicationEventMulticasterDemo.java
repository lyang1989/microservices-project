package com.princeli.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-02-14 09:20
 **/
public class ApplicationEventMulticasterDemo {
    public static void main(String[] args) {
        ApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();

        multicaster.addApplicationListener(event -> {

            if (event instanceof PayloadApplicationEvent){
                System.out.println("接收到PayloadApplicationEvent事件："+PayloadApplicationEvent.class.cast(event).getPayload());
            }else{
                System.out.println("接收到事件："+event);
            }

        });

        //发布/广播事件
        multicaster.multicastEvent(new MyEvent("hello word"));
        multicaster.multicastEvent(new PayloadApplicationEvent<Object>("2","hello word"));

    }


    private static class MyEvent extends ApplicationEvent {


        public MyEvent(Object source) {
            super(source);
        }
    }
}
