package edu.durand.GerenciamentoLocais.application.dto;

import jakarta.validation.constraints.NotBlank;

public record LocationDTO(@NotBlank(message = "NOME NAO NUL") String name, String cep, String number, String complement) {
}
