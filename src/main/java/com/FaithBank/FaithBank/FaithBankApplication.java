package com.FaithBank.FaithBank;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
			title = "Faith Bank Application",
			description = "Backend Rest API  Documentation for Faith Bank",
            version = "v2.0",
			contact = @Contact(
			          email = "okosunfaith2@gmail.com",
			          url = "https://github.com/FAITHOKOSUN/FaithBank"
			),
			license = @License(
					name = "Faith bank application",
					url  = "https://github.com/FAITHOKOSUN/FaithBank"
			)
	),
		externalDocs = @ExternalDocumentation(
				description = "Faith bank application documentation",
				url  = "https://github.com/FAITHOKOSUN/FaithBank"
		)

)

public class FaithBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(FaithBankApplication.class, args);
	}

}
