package br.com.jatao;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@OpenAPIDefinition(
		info = @Info(
				title = "Gestão",
				description = " Documentação  REST API Gestão de serviços",
				version = "v1",
				contact = @Contact(
						name = "Trentor",
						email = "contato@trentor.com.br",
						url = "https://www.trentor.com.br"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.trentor.com.br"
				)
		),
		externalDocs = @ExternalDocumentation(
				description =  "Trentor desenvolvimento de sistemas",
				url = "https://www.trentor.com.br"
		)
)
@SpringBootApplication
@EnableFeignClients
public class JataoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JataoApplication.class, args);
	}

}
