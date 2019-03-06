# Catálogo de Pessoas

Essa aplicação permite inserir dados de pessoas e recuperar essa informação através de uma **api** ou de sua interface gráfica web.

### Execução
A aplicação pode clonada desse repositório. 

Banco de dados, o projeto utilizou o banco [PostgreSql](http://www.postgresql.org/) para persistir os dados. Dessa forma é necessário instalar esse banco de dados e criar o usuário **people** com senha **postgres**.

A aplicação está configurada para ao iniciar criar as tabelas no banco de dados e ao finalizar a sessão, apagar esses dados.

Com o PostgreSql e o Maven instalados e rodando, abrir a pasta da aplicação e executar o comando: `mvn spring-boot:run`.

### Api
A api da aliplicação é composta pelos endpoints:
* Recuperar todas as pessoas: Get - http://localhost:8080/rest/pessoas
* Recuperar apenas uma pessoa: Get - http://localhost:8080/rest/pessoa/{id}
* Inserir uma pessoa: Post - http://localhost:8080/rest/pessoa/save
* Remover uma pessoa: Delete - http://localhost:8080/rest/pessoa/remove/{id}

O endpoint para inserir uma pessoa recebe um *Resquest Body* com um Json com os dados daquela pessoa.

**Ex:**
http://localhost:8080/rest/pessoa/save
```json
{
	"name":"Leandro Lima",
	"street":"Maria Conceição Patrus",
	"number":"5",
	"neighborhood":"Santa Amélia",
	"city":"Belo Horizonte",
	"state":"Minas Gerais",
	"cellphone":"31-99223-6776",
	"phone":"31-3495-4365"
}
```
Deve retornar:
```json
{
    "result": "Success"
}
```
O endpoint para obter uma pessoa específica recebe um **Path Parameter** com o *id* da pessoa e retorna um Json com os dados da pessoa.
```json
{
    "id": 42,
    "name": "Leandro Lima",
    "street": "Maria Conceição Patrus",
    "number": 5,
    "neighborhood": "Santa Amélia",
    "city": "Belo Horizonte",
    "state": "Minas Gerais",
    "cellphone": "31-99223-6776",
    "phone": "31-3495-4365"
}
```
O endpoint para obter todas as pessoas retorna um array de pessoas com formato Json.

Já o endpoint para deleção de pessoa deve receber também o **Path Parameter** com o *id* a ser removido e seu retorno é uma resposta de sucesso.
```json
{
    "result": "Success"
}
```

**Exceções:**
Caso o usuário tente recuperar ou remover qualquer pessoa com *id* não encontrado, o sistema retornará uma resposta de **Bad Request** com a seguinte mensagem:
```json
{
    "timestamp": "2019-03-06T12:42:40.124+0000",
    "status": 400,
    "error": "Bad Request",
    "message": "Person with id 38 does not exist.",
    "path": "/rest/pessoa/38"
}
```

### Frontend
O frontend foi desenvolvido utilizando AngularJs, Bootstrap e Html. A interface é bastante simples mas cumpre o requisito de utilizar todos os endpoints da API. Para acessá-la, basta, com a aplicação rodando, abrir o browser com a url:

* http://localhost:8080/

Foi adotado uma tabela para mostrar as pessoas inseridas e nas mesma tabela no seu início é possível inserir novas pessoas. Em cada linha é possível remover a pessoa. Nessa ação é aberta uma caixa de confirmação que busca o nome da pessoa naquele *id* e retorna no corpo da mensagem de confirmação de deleção.

### Stack
O projeto foi criado utilizando Java 8, Maven, Sprint Boot, PostgreSql, AngularJs e Bootstrap. Foram criados testes unitários para o *service* e testes de integração sobre o *controler* usando o banco em memória H2 para execução dos teste.

#### Persistência
Optou-se pelo banco PostgreSql para se mostrar as diferenças entre um banco de teste e um banco de aplicação. 

