package com.mqmft.rest.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@ComponentScan("com.mqmft.*")
@SpringBootApplication
public class MQMFT_Application extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(MQMFT_Application.class, args);
	}

}

