package edu.durand.GerenciamentoLocais.application.dto;

import edu.durand.GerenciamentoLocais.domain.model.UserRole;

public record RegisterDTO(String username, String password, UserRole role) {
}
