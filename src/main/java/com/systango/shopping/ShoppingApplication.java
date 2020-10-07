package com.systango.shopping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ShoppingApplication {
	   
    private static final Logger logger = LoggerFactory.getLogger(ShoppingApplication.class);

	public static void main(String[] args) {
		
        logger.info("Application started");
		SpringApplication.run(ShoppingApplication.class, args);
	}

}
