package edu.durand.GerenciamentoLocais;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Gerenciador de Locais", version = "1",
		description = "API para o desafio da técnico da Nuven"))
public class GerenciamentoLocaisApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciamentoLocaisApplication.class, args);
	}

}
