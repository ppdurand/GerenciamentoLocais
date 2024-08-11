package edu.durand.GerenciamentoLocais.domain.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Embedded
    private Address address;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

    public Location() {}

    public Location(String name, Address address) {
        this.name = name;
        this.address = address;
        this.creationDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }
    public Location(String name, String cep, String number, String complement){
        this.name = name;
        this.address.setCep(cep);
        this.address.setNumero(number);
        this.address.setComplemento(complement);
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

    public LocalDateTime getUpdateDate() {
        return updateDate;
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

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
