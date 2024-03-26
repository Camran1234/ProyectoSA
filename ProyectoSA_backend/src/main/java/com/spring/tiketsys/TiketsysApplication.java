package com.spring.tiketsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
/*@ComponentScan(basePackages = {"com.spring.tiketsys.config", "com.spring.tiketsys.controller.*",
								"com.spring.tiketsys.repository", "com.spring.tiketsys.service",
								"com.spring.tiketsys.security.*"})*/
public class TiketsysApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiketsysApplication.class, args);
	}

	@RestController
	public static class Tests{


		@PostMapping("/hello")
		public String hello(){
			return "Hello";
		}


	}


}
