package com.princeli.micro.services.spring.cloud.client.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-27 15:28
 **/
public class SpringAnnotationDrivenEvent {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringAnnotationDrivenEvent.class);
        context.refresh();

        context.publishEvent(new MyApplicationEvent("hello,word"));

        context.close();

    }


    private static class MyApplicationEvent extends ApplicationEvent {
        public MyApplicationEvent(Object source){
            super(source);
        }

    }

//    @EventListener
//    public void onMessage(MyApplicationEvent event){
//        System.err.println("监听到MyApplicationEvent 事件源:" + event.getSource());
//    }

    @EventListener
    public void onMessage(Object eventSource){
        System.err.println("监听到MyApplicationEvent 事件源eventSource:" + eventSource);
    }


}


