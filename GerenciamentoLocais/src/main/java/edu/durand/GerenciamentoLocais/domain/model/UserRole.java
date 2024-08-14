package edu.durand.GerenciamentoLocais.domain.model;

public enum UserRole {
    ADMIN("Admin"), REGULAR("Regular");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
