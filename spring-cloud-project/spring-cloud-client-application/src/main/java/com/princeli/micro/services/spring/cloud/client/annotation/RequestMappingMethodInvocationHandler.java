package com.princeli.micro.services.spring.cloud.client.annotation;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-11 10:44
 **/
public class RequestMappingMethodInvocationHandler implements InvocationHandler {

    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    private final String serviceName;

    private final BeanFactory beanFactory;

    public RequestMappingMethodInvocationHandler(String serviceName,BeanFactory beanFactory) {
        this.serviceName = serviceName;
        this.beanFactory = beanFactory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //过滤 @RequestMapping 方法
        GetMapping getMapping = findAnnotation(method, GetMapping.class);
        if (getMapping != null){
            //得到URI
            String [] uri = getMapping.value();

            //http://${serviceName}/${uri}
            StringBuilder urlBuilder = new StringBuilder("http://").append(serviceName).append("/").append(uri[0]);

            //获取方法的数量
            int count = method.getParameterCount();
            //方法参数名称集合
            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
            //方法参数类型集合
            Class<?>[] paramTypes = method.getParameterTypes();

            //方法参数的二维数组注解
            Annotation [][] annotations = method.getParameterAnnotations();

            //Annotation[] annotations = method.getDeclaredAnnotations();

            StringBuilder queryStringBuilder = new StringBuilder();

            for (int i= 0;i<count;i++){
                Annotation[] paramAnnotations = annotations[i];
                RequestParam requestParam = (RequestParam)paramAnnotations[0];

                Class<?> paramType = paramTypes[i];
               // RequestParam requestParam = method.getAnnotation(RequestParam.class);
                if (requestParam != null){
                    String paramName = parameterNames[i];
                    //http请求参数
                    String requestParamName = StringUtils.hasText(requestParam.value()) ? requestParam.value():paramName;

                    String requestParamValue =String.class.equals(paramType) ? (String) args[i]:String.valueOf(args[i]);


                    //uri?name=value&a=1&b=1
                    queryStringBuilder.append("&")
                            .append(requestParamName)
                            .append("=")
                            .append(requestParamValue);


                }

            }

            String queryString = queryStringBuilder.toString();
            if (StringUtils.hasText(queryString)){
                urlBuilder.append("?").append(queryString);
            }

            //http://${serviceName}/${url}?${queryString}
            String url = urlBuilder.toString();

            //获取RestTemplate
            RestTemplate restTemplate = beanFactory.getBean("loadBalancedRestTemplate", RestTemplate.class);

            return restTemplate.getForObject(url,method.getReturnType());



        }


        return null;
    }
}
