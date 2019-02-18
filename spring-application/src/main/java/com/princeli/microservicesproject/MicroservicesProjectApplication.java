package com.princeli.microservicesproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
public class MicroservicesProjectApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(MicroservicesProjectApplication.class);
		Map<String,Object> properties = new LinkedHashMap<>();
		properties.put("server.port",0);
		springApplication.setDefaultProperties(properties);

		springApplication.setWebApplicationType(WebApplicationType.NONE);
		ConfigurableApplicationContext context = springApplication.run(args);
		System.out.println(context.getBean(MicroservicesProjectApplication.class));

		System.out.println(context.getClass().getName());


//		SpringApplication.run(MicroservicesProjectApplication.class, args);
//		new SpringApplicationBuilder(MicroservicesProjectApplication.class)
//				.properties("server.port=0")
//				.run(args);
	}

}

