package com.example.antchain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.example"})
@ComponentScan(basePackages ={ "com.example.mapper"})

public class AntchainApplication {

	public static void main(String[] args) {
		SpringApplication.run(AntchainApplication.class, args);
	}

}
