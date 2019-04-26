package com.mqrest.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import javafx.application.Application;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan("com.mqrest.*")
public class MqRestApiApplication extends SpringBootServletInitializer   {

	public static void main(String[] args) {
		SpringApplication.run(MqRestApiApplication.class, args);
	}
	

}