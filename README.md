# Gerenciador de Locais
## Tópicos da Documentação
- [Rotas](#Rotas)
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
