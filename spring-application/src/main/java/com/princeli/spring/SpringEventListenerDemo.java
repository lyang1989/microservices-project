package com.princeli.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-01-24 14:03
 **/
public class SpringEventListenerDemo {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();


        context.addApplicationListener(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                System.out.println("监听事件:"+event);
            }
        });
//        context.addApplicationListener(new ClosedListener());

        context.refresh();


        context.publishEvent("HelloWorld");


        context.publishEvent(new MyEvent("HelloWorld 2018"));

        context.close();
    }

    private static class ClosedListener implements ApplicationListener<ContextClosedEvent>{

        @Override
        public void onApplicationEvent(ContextClosedEvent event) {
            System.out.println("关闭上下文："+event);
        }
    }


    private static class MyEvent extends ApplicationEvent{


        public MyEvent(Object source) {
            super(source);
        }
    }


}
