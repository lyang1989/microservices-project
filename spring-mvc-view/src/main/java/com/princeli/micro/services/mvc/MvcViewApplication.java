package com.princeli.micro.services.mvc;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = "com.princeli.micro.services.mvc.controller")
public class MvcViewApplication {

	public static void main(String[] args) {
		 new SpringApplicationBuilder(MvcViewApplication.class)
				 .run(args);
	}

}

