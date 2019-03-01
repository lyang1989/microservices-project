package com.princeli.micro.services.spring.cloud.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-02-27 14:00
 **/
@SpringBootApplication
@EnableConfigServer
public class SpringCloudConfigServer {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigServer.class,args);
    }


    @Bean
    public EnvironmentRepository environmentRepository(){
        return (String application, String profile, String label) ->{
            Environment environment = new Environment("default",profile);

            List<PropertySource> propertiesPropertySources = environment.getPropertySources();
            Map<String,Object> source = new HashMap<>();
            source.put("name","小马哥");
            PropertySource propertySource = new PropertySource("map",source);
            propertiesPropertySources.add(propertySource);

            return environment;

        };
    }
}
