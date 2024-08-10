package edu.durand.GerenciamentoLocais.domain.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Location {
    @Id
    private long id;
    private String name;
    @Embedded
    private Address address;
    private LocalDateTime creationDate;

    public Location() {}

    public Location(String name, Address address, LocalDateTime creationDate) {
        this.name = name;
        this.address = address;
        this.creationDate = creationDate;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
