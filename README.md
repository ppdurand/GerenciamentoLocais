# Gerenciador de Locais
## Tópicos da Documentação
- [Como Usar a API](#Como-Usar-a-API)
- [Rotas](#Rotas)
- [API Externa](#API-Externa)
- [Banco de Dados](#Banco-de-Dados)
- [Testes Unitários](#Testes-Unitários)
## Como Usar a API
### Usando o Docker
Para usar essa API com o Docker, rode o comando abaixo na raiz do projeto:
```terminal
docker-compose up -d
```
Agora, para acessar todos os Endpoints, basta colocar antes das rotas de cada um essa porta:
```http
http://localhost:8080
```
### Clonando o projeto inteiro
Após o clone deste repositório na sua máquina, confira se você possui o banco MySQl. Para informações das configurações do banco, vá para o tópico de [Banco de Dados](#Banco-de-Dados).

Com o Banco configurado, comece a rodar o projeto. Para acessar o Endpoints, você pode utilizar a plataforma de consumo de API de sua preferência, porém, esse projeto tem suporte para Swagger, onde os Endpoints estarão todos listados e de fácil acesso.
O link para acessar o Swagger durante a aplicação:
```http
http://localhost:8080/swagger-ui/index.html
```
## Rotas
Obs: As rotas de lugar requerem estar conectados com um Bearer Token, logo, as rotas de autenticação precisam ser acessadas antes.
## Criando um usuário
Essa rota registra um novo usuario no banco
```http
POST /auth/register
```
O Json de entrada deve seguir o seguinte modelo
```json
{
  "username": "PedroDurand",
  "password": "123",
  "role": "ADMIN"
}
```
Em "role", 2 valores são aceitos: ADMIN e REGULAR. Algumas rotas não permitem o acesso de um usuário regular.

## Logando com um usuário
Com o usuario criado, essa rota deve ser acessada
```http
POST /auth/register
```
Modelo de Json:
```json
{
  "username": "PedroDurand",
  "password": "123"
}
```
Essa rota retornará um token, que deverá ser colocado em cada requisição para a camada de segurança permitir o acesso. No postman, você deve escolher o tipo de autenticação "Bearer Token".

Observações, todas as rotas
### Retornando Locais 
#### Por Ordem de Criação
Essa rota retorna uma lista de todos os locais cadastrados no sistema, ordenados pela data de criação (da mais antiga para a mais recente).
```http
  GET /locations
```
Um JSON com todos os locais ordenados será retornado.

#### Pelos Mais Recentes (Apenas Admins)
Rota que retorna uma lista de todos os locais cadastrados no sistema, ordenados por data de criação mais recente.
```http
  GET /locations/recent/
```
Apenas Admins podem acessar essa rota.

### Por ID (Apenas Admins)
Se o objetivo é apenas retornar um local especifico, existe a rota onde se passa o ID do lugar criado
```http
  GET /locations/search/{id}
```
Apenas Admins podem acessar essa rota.

### Criando Local Novo 
Rota que permite o usuário criar um novo local
```http
  POST /locations/new
```
Para essa rota, um JSON no corpo da requisição é necessário, aqui um exemplo:
```json
{
    "name": "Minha Casa",
    "cep": "60761-320",
    "number": "1",
    "complement": "Lado Impar"    
}
```
O que digitar nos campos:
- Nome(name): Nome do local, empresa, estabelecimento ou algo customizável pelo usuário.
- CEP: Qualquer CEP válido em território brasileiro. Pode ser inserido com ou sem traço.
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
## Camada de Segurança
Para a camada de segurança, usei o Spring Security e o Token JWT. Ela fica responsavel em criar um usuario e fazer o login do mesmo. Usuários comuns (Regular) terão acesso à todas as rotas que eram requisitos obrigatorios do projeto, já os Admins tem acesso também a rotas que não estavam especificadas.
