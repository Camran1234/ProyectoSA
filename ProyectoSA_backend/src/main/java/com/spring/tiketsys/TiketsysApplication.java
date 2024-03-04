package com.spring.tiketsys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
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
