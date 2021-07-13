package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.example"})
@ComponentScan(basePackages ={"com.example"})
public class WhuIseApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhuIseApplication.class, args);
	}

}
