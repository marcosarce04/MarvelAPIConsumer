package com.example.MarvelAPIConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages="com.example")
@EntityScan("com.example.Entities")
@EnableJpaRepositories("com.example.Dao")
public class MarvelApiConsumerApplication {

	public static void main(String[] args) {

		SpringApplication.run(MarvelApiConsumerApplication.class, args);

	}

}
