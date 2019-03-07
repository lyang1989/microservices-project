package com.princeli.micro.services.spring.cloud.server.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.princeli.micro.services.spring.cloud.server.annotation.SemaphoreCircuitBreaker;
import com.princeli.micro.services.spring.cloud.server.annotation.TimeoutCircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-04 09:31
 **/
@RestController
public class ServerController {

    private final static Random random = new Random();

    @Value("${spring.application.name}")
    private String currentServiceName;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();



    /**
     * 简易版本
     * @param message
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/say2")
    public String say2(@RequestParam String message) throws Exception {
        Future<String> future = executorService.submit(()->{
           return doSay2(message);
        });

        String returnValue = null;

        try{
            returnValue = future.get(100, TimeUnit.MILLISECONDS);
        }catch (InterruptedException | ExecutionException | TimeoutException e){
            returnValue = errorContent(message);
        }

        return returnValue;
    }

    /**
     * 中级版本
     * @param message
     * @return
     * @throws Exception
     */
    @GetMapping("/middle/say")
    public String middleSay(@RequestParam String message) throws Exception {
        Future<String> future = executorService.submit(()->{
            return doSay2(message);
        });

        //100毫秒超时
        String returnValue = null;
        try{
            returnValue = future.get(100, TimeUnit.MILLISECONDS);
        }catch (TimeoutException e){
            future.cancel(true);
            throw e;
        }

       return returnValue;

    }

    /**
     * 高级版本
     * @param message
     * @return
     * @throws Exception
     */
    @GetMapping("/advanced/say")
    public String advancedSay(@RequestParam String message) throws Exception {
         return doSay2(message);
    }

    /**
     * 高级版本+超时注解
     * @param message
     * @return
     * @throws Exception
     */
    @GetMapping("/advanced/say2")
    @TimeoutCircuitBreaker(timeout = 100)
    public String advancedSay2(@RequestParam String message) throws Exception {
        return doSay2(message);
    }

    /**
     * 高级版本+信号量注解
     * @param message
     * @return
     * @throws Exception
     */
    @GetMapping("/advanced/say3")
    @SemaphoreCircuitBreaker(1)
    public String advancedSay3(@RequestParam String message) throws Exception {
        return doSay2(message);
    }



    private String doSay2(String message) throws InterruptedException {
        int value = random.nextInt(200);
        System.out.println("say2() costs "+value+" ms.");
        Thread.sleep(value);
        String returnValue = "say2:" + message;
        System.out.println(returnValue);
        return returnValue;
    }

    @HystrixCommand(
            fallbackMethod = "errorContent",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "100")
            }

    )
    @GetMapping("/say")
    public String say(@RequestParam String message) throws InterruptedException {
        int value = random.nextInt(200);

        System.out.println("say() costs "+value+" ms.");

        Thread.sleep(value);

        System.out.println("ServerController 接收到消息 - say:" + message);
        return "hello,"+message;

    }

    public String errorContent(String message) {
        return "Fault";
    }

}
