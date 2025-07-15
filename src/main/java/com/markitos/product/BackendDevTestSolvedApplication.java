package com.markitos.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class BackendDevTestSolvedApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendDevTestSolvedApplication.class, args);
	}

}
