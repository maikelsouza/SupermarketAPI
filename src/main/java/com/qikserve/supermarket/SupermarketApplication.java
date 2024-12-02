package com.qikserve.supermarket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SupermarketApplication {

	private static final Logger logger = LogManager.getLogger(ApplicationArguments.class);

	public static void main(String[] args) {

		SpringApplication.run(SupermarketApplication.class, args);
		logger.info("Spring Boot application started with Log4j!");
	}

}
