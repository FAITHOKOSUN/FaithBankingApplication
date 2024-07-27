package com.FaithBank.FaithBank;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
			title = "Faith Bank Application",
			description = "Backend Rest API  Documentation for Faith Bank"

	)
)

public class FaithBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(FaithBankApplication.class, args);
	}

}
