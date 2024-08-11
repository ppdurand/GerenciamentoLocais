package edu.durand.GerenciamentoLocais.infra.client;

import com.google.gson.Gson;
import edu.durand.GerenciamentoLocais.domain.model.Address;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Component
public class ViaCepClient {
    //Consultando enndereço via CEP
    public Address cepConsult(String cep) throws IOException {
        URL url = new URL("https://viacep.com.br/ws/"+ cep +"/json/");

        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

        String responseCep = "";
        StringBuilder jsonCep = new StringBuilder();

        while((responseCep = buffer.readLine()) != null){
            jsonCep.append(responseCep);
        }

        return new Gson().fromJson(jsonCep.toString(), Address.class);
    }
}
