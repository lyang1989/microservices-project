package com.princeli.micro.services.spring.cloud.server.aop;

import com.princeli.micro.services.spring.cloud.server.annotation.SemaphoreCircuitBreaker;
import com.princeli.micro.services.spring.cloud.server.annotation.TimeoutCircuitBreaker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.lang.reflect.Method;
import java.util.concurrent.*;

/**
 * @program: microservices-project
 * @description: 切面
 * @author: ly
 * @create: 2019-03-06 14:11
 **/
@Aspect
@Component
public class ServerControllerAspect {

    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    private volatile Semaphore semaphore = null;

    @Around("execution(* com.princeli.micro.services.spring.cloud.server.controller.ServerController.advancedSay(..)) " +
            "&& args(message) ")
    public Object advancedSayInTimeout(ProceedingJoinPoint point,String message) throws Throwable {
        return doInvoke(point,message,100);
    }

//    @Around("execution(* com.princeli.micro.services.spring.cloud.server.controller.ServerController.advancedSay2(..)) && args(message) && @annotation(circuitBreaker)")
//    public Object advancedSay2InTimeout(ProceedingJoinPoint point, String message, TimeoutCircuitBreaker circuitBreaker) throws Throwable {
//        long timeout = circuitBreaker.timeout();
//        return doInvoke(point,message,timeout);
//    }

    @Around("execution(* com.princeli.micro.services.spring.cloud.server.controller.ServerController.advancedSay2(..)) " +
            "&& args(message)")
    public Object advancedSay2InTimeout(ProceedingJoinPoint point, String message) throws Throwable {
        long timeout = -1;
        if (point instanceof MethodInvocationProceedingJoinPoint){
            MethodInvocationProceedingJoinPoint methodPoint = (MethodInvocationProceedingJoinPoint)point;
            MethodSignature methodSignature = (MethodSignature)methodPoint.getSignature();
            Method method = methodSignature.getMethod();
            TimeoutCircuitBreaker circuitBreaker = method.getAnnotation(TimeoutCircuitBreaker.class);
            timeout = circuitBreaker.timeout();
        }
        return doInvoke(point,message,timeout);
    }



    @Around("execution(* com.princeli.micro.services.spring.cloud.server.controller.ServerController.advancedSay3(..)) " +
            "&& args(message) " +
            "&& @annotation(semaphoreCircuitBreaker)")
    public Object advancedSay3InSemaphore(ProceedingJoinPoint point, String message,SemaphoreCircuitBreaker semaphoreCircuitBreaker) throws Throwable {
        int value = semaphoreCircuitBreaker.value();
        if (semaphore == null){
            semaphore = new Semaphore(value);
        }
        Object returnValue = null;
        try {
            if(semaphore.tryAcquire()){
                returnValue = point.proceed(new String[]{message});
            }else{
                returnValue = errorContent(message);
            }
        }finally {
            semaphore.release();
        }
        return returnValue;
    }


    public String errorContent(String message) {
        return "Fault";
    }

    @PreDestroy
    public void destory(){
        executorService.shutdown();
    }


    private Object doInvoke(ProceedingJoinPoint point, String message, long timeout) throws Throwable {
        Future<Object> future = executorService.submit(() -> {
            Object returnValue = null;
            try {
                returnValue = point.proceed(new String[]{message});
            } catch (Throwable throwable) {
                //throwable.printStackTrace();
            }
            return returnValue;

        });

        Object returnValue = null;
        try{
            returnValue = future.get(timeout,TimeUnit.MILLISECONDS);
        } catch (TimeoutException e){
            future.cancel(true);
            returnValue = errorContent(message);
        }
        return returnValue;
    }

}
