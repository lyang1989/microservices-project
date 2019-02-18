package com.princeli.micro.services.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MvcRestApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(MvcRestApplication.class);
		springApplication.run(args);
	}

}

