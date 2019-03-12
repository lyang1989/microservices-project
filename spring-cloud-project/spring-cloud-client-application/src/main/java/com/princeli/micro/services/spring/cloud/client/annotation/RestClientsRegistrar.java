package com.princeli.micro.services.spring.cloud.client.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-11 09:44
 **/
public class RestClientsRegistrar implements ImportBeanDefinitionRegistrar , BeanFactoryAware, EnvironmentAware {

    private BeanFactory beanFactory;

    private Environment environment;


    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        ClassLoader classLoader = metadata.getClass().getClassLoader();
        Map<String,Object> attributes = metadata.getAnnotationAttributes(EnableRestClient.class.getName());

        Class<?>[] clientsClasses = (Class<?>[])attributes.get("clients");

        Stream.of(clientsClasses)
                .filter(Class::isInterface)
                .filter(interfaceClass->
                    findAnnotation(interfaceClass, RestClient.class)!=null)//仅选择标注@RestClient
                .forEach(restClientClass->{
                    //获取@RestClient元素
                    RestClient restClient = findAnnotation(restClientClass, RestClient.class);

                    //获取应用名称（出来占位符）
                    String serviceName = environment.resolvePlaceholders(restClient.name());


                    //@RestClient 接口变成JDK动态代理
                    Object proxy = Proxy.newProxyInstance(classLoader, new Class[]{restClientClass}, new RequestMappingMethodInvocationHandler(serviceName,beanFactory));

                    String beanName = "RestClient." + serviceName;
                    if (registry instanceof SingletonBeanRegistry){
                        SingletonBeanRegistry singletonBeanRegistry = (SingletonBeanRegistry)registry;
                        singletonBeanRegistry.registerSingleton(beanName,proxy);
                    }


//                    registerBeanByFactoryBean(serviceName,proxy,restClientClass,registry);
                });

    }


    private static void registerBeanByFactoryBean(String serviceName,Object proxy,Class<?> restClientClass,BeanDefinitionRegistry registry){
        String beanName = "RestClient." + serviceName;

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(RestClientClassFactoryBean.class);
        beanDefinitionBuilder.addConstructorArgValue(proxy);
        beanDefinitionBuilder.addConstructorArgValue(restClientClass);

        BeanDefinition beanDefinition =beanDefinitionBuilder.getBeanDefinition();
        registry.registerBeanDefinition(beanName,beanDefinition);
    }

    private static class RestClientClassFactoryBean implements FactoryBean{

        private final Object proxy;

        private final Class<?> restClientClass;


        public RestClientClassFactoryBean(Object proxy,Class<?> restClientClass) {
            this.proxy = proxy;
            this.restClientClass = restClientClass;
        }

        @Override
        public Object getObject() throws Exception {
            return proxy;
        }

        @Override
        public Class<?> getObjectType() {
            return restClientClass;
        }
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
