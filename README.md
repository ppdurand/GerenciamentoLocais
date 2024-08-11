# Gerenciador de Locais
## Tópicos da Documentação
- [Rotas](#Rotas)
- [API Externa](#APIExterna)
- [Banco de Dados](#BancodeDados)
- [Testes Unitários](#TestesUnitários)
- [Dependências](#Dependências)
## Rotas

### Retornando Locais 
#### Por Ordem de Criação
Essa rota retorna uma lista de todos os locais cadastrados no sistema, ordenados pela data de criação (da mais antiga para a mais recente).
```http
  GET /locations
```
Um JSON com todos os locais ordenados será retornado.

#### Pelos Mais Recentes
Rota que retorna uma lista de todos os locais cadastrados no sistema, ordenados por data de criação mais recente.
```http
  GET /locations/recent/
```
Um JSON com todos os locais ordenados será retornado
### Criando Local Novo 
Rota que permite o usuário criar um novo local
```http
  POST /locations/new
```
Para essa rota, um JSON no corpo da requisição é necessário, aqui um exemplo:
```json
{
    "name": "Minha Casa",
    "cep": "60761320",
    "number": "1",
    "complement": "Lado Impar"    
}
```
O que digitar nos campos:
- Nome(name): Nome do local, empresa, estabelecimento ou algo customizável pelo usuário.
- CEP: Qualquer CEP válido em território brasileiro. Deve ser inserido com apenas números, sem traços.
- Número(number): Número do endereço que o usuário quer cadastrar.
- Complemento(complement): Caso houver e for necessário, um complemento que facilite o encontro do local.

### Atualizando um Local
A rota que permite o usuário editar informações de um local é:
```http
PUT /locations/update/{id}
```
O usuário deve colocar o ID do local já cadastrado na rota para o sistema realizar a busca.
No corpo da requisição, um JSON, similar ao de criação, deverá estar presente.
```json
{
    "name": "Minha Casa",
    "cep": "60040531",
    "number": "2083",
    "complement": "Ao lado do IFCE"    
}
```
Nesse exemplo, se presume que o usuário se mudou e alterou informações relacionadas ao endereço e mantendo apenas o nome do local.

### Deletando um Local
A seguinte rota permite o usuário deletar um local
```http
DELETE /locations/delete/{id}
```
O usuário deve informar na rota um ID do local cadastrado que queira deletar

## API Externa
Para cadastrar um novo local, o usuário deve informar um CEP. Por meio desse CEP, o sistema saberá o estado, cidade, bairro e logradouro (rua, avenida, etc). Isso só é possível pelo uso da API Via CEP.

A documentação oficial da API pode ser vista [aqui](https://viacep.com.br).

Em comparação com outras API's, essa tem uma implementação simplificada. Por ser pública, não precisará de uma chave pra ser usada e o uso de dependências para o consumo de API's externas (Open Feign Client, por exemplo) não serão necessárias.

Isso facilita o uso do código para outras pessoas que não estiveram envolvidas no desenvolvimeto do projeto, pois a consulta pode ser feita por qualquer um.

### Implementação
Para fazer a consulta do endereço por CEP, o sistema usa a rota:
```http
https://viacep.com.br/ws/{CEP}/json/
```
Na regra de negócio, a string enviada como CEP pelo usuário é colocado em {CEP}. Para estabelecer uma conexão com a API, o seguinte código é executado:
```java
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
```
Esse trecho de código se utiliza de uma dependêcia, a Gson, que facilita a conversão entre objetos Java e JSON.
```xml
<dependency>
  <groupId>com.google.code.gson</groupId>
  <artifactId>gson</artifactId>
  <version>2.11.0</version>
</dependency>
```
Como se deve imaginar, para o funcionamento correto do projeto, uma conexão com a internet deve estar estabelecida.

## Banco de Dados
O banco de dados usado no projeto é o [MySQL](https://www.mysql.com). Para rodar o projeto corretamente, é necessário a instalação desse banco na máquina, e a criação da database "gerenciadordelocais". Também, no arquivo application.properties, é preciso alterar o usuário e a senha pelos os que foram definidos na instalação.
```properties
spring.datasource.username=root # Usuário, por padrão vem 'root'
spring.datasource.password=${DB_PASSWORD} # Senha, que no caso está definida por variável de ambiente
```

## Testes Unitários
Para realizar os testes unitários, o banco utilizado foi o banco H2, um banco relacional em memória, muito utilizado para testes rápidos e simples
```xml
<dependency>
		<groupId>com.h2database</groupId>
		<artifactId>h2</artifactId>
		<scope>runtime</scope>
</dependency>
```
## Dependências
Este projeto se utiliza das seguintes dependêcias:
- Starter Web, facilita a criação de aplicações web.
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
- Starter Data JPA, facilita a integração e o uso do Spring Data JPA em projetos Java.
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```
- MySql Connector, permite que aplicações Java se conectem a bancos de dados MySQL.
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```
H2, permite que aplicações Java se conectem a bancos de dados H2.
```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```
Starter Test, padrão do Spring que fornece uma configuração pré-configurada para testes em aplicações para o framework.
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```
Gson, facilita a conversão entre objetos Java e JSON
```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.11.0</version>
</dependency>
```
