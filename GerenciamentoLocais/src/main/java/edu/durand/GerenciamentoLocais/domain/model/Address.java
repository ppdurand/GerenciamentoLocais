package edu.durand.GerenciamentoLocais.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String CEP;
    private String state;
    private String city;
    private String district;
    private String street;
    private String number;
    private String complement;

    public Address() {
    }

    public Address(String CEP, String state, String city, String district,
                   String street, String number, String complement) {
        this.CEP = CEP;
        this.state = state;
        this.city = city;
        this.district = district;
        this.street = street;
        this.number = number;
        this.complement = complement;
    }

    public String getCEP() {
        return CEP;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }
}
