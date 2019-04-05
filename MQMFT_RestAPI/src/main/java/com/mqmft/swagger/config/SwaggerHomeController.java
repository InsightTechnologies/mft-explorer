package com.mqmft.swagger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;

@Configuration
@ApiIgnore
public class SwaggerHomeController {

	private String Home_Controller = "redirect:swagger-ui.html";

	@RequestMapping(value = "/")
	public String homeIndex() {
		return Home_Controller;

	}
}
