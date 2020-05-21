package com.example.MarvelAPIConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.example")
public class MarvelApiConsumerApplication {

	public static void main(String[] args) {

		SpringApplication.run(MarvelApiConsumerApplication.class, args);

	}

}
