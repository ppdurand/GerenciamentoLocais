package edu.durand.GerenciamentoLocais.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Address {
    private String cep;
    private String uf;
    private String localidade;
    private String bairro;
    private String logradouro;
    private String numero;
    private String complemento;

    public Address() {
    }

    public Address(String cep, String uf, String localidade, String bairro,
                   String logradouro, String numero, String complemento) {
        this.cep = cep;
        this.uf = uf;
        this.localidade = localidade;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(cep, address.cep) &&
                Objects.equals(logradouro, address.logradouro) &&
                Objects.equals(numero, address.numero) &&
                Objects.equals(complemento, address.complemento) &&
                Objects.equals(bairro, address.bairro) &&
                Objects.equals(localidade, address.localidade) &&
                Objects.equals(uf, address.uf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cep, logradouro, numero, complemento, bairro, localidade, uf);
    }

}
