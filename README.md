# API de Gerenciamento de Clube de Campo

## Sobre o Projeto

Este projeto consiste no desenvolvimento de uma API RESTful para o gerenciamento das atividades de um clube de campo. A aplicação foi criada como parte de um trabalho acadêmico, com o objetivo de aplicar conceitos de desenvolvimento backend, arquitetura de software e persistência de dados.

## Enunciado do Problema

O sistema foi projetado para atender às necessidades de **Miguel**, o gerente de um clube de campo, que contratou o desenvolvimento de um software para melhorar a gestão das atividades relacionadas aos associados.

O clube possui três quadras poliesportivas, duas quadras de futebol de salão, uma sala de jogos, dois salões de festas, três piscinas (infantil e adulto com tobogã), parque infantil, sala de recreação com monitor (para crianças entre 2 e 7 anos), além de haras, campo de golfe, área para realização de trilhas e quinze churrasqueiras.

Existem três tipos de associados: Standard, Gold e Black. Para o cadastro, são necessários: Nome, RG, CPF, endereço, CEP, bairro, cidade, estado, telefone residencial, telefone comercial, celular e data de cadastro. O associado pode cadastrar dependentes (nome completo e RG). Para a utilização do clube, os associados devem realizar reservas das dependências. Os pagamentos são mensais e as cobranças são geradas e administradas pelo sistema, com acréscimo de 5% por atraso. A inadimplência progressiva resulta em restrições: após dois meses, perdem acesso ao haras, campo de golfe e piscina; após três meses, acesso apenas às quadras; após quatro meses, a carteirinha é bloqueada. O sistema deve registrar pagamentos (valor, forma, data) e controlar as reservas das áreas, cadastrando cada área e sua capacidade. Áreas reserváveis incluem churrasqueiras e salões de festa. Os passeios no haras são agendados em 10 turmas de até 20 pessoas. As trilhas duram 1h30min, com 7 turmas diárias de até 30 pessoas.

## Tecnologias Utilizadas

Este projeto foi construído utilizando as seguintes tecnologias e frameworks:

* **Java 17**: Linguagem de programação principal.
* **Spring Boot 3**: Framework para criação da aplicação e da API RESTful.
* **Spring Data JPA & Hibernate**: Para a camada de persistência e mapeamento objeto-relacional.
* **MySQL**: Banco de dados relacional para armazenamento dos dados.
* **Maven**: Ferramenta para gerenciamento de dependências e build do projeto.
* **Springdoc OpenAPI (Swagger)**: Para geração automática de documentação interativa da API.

## Funcionalidades

A API oferece um conjunto completo de endpoints para gerenciar os principais recursos do clube:

* **Gestão de Associados:**
    * `POST /associados`: Cadastro de novos associados, incluindo endereço e dependentes.
    * `GET /associados`: Listagem paginada de todos os associados.
    * `GET /associados/{id}`: Busca de um associado por ID.
    * `PUT /associados/{id}`: Atualização dos dados cadastrais de um associado.
    * `DELETE /associados/{id}`: Remoção de um associado.

* **Gestão de Dependentes:**
    * `POST /associados/{associadoId}/dependentes`: Adição de um novo dependente a um associado.
    * `PUT /dependentes/{id}`: Atualização dos dados de um dependente.
    * `DELETE /dependentes/{id}`: Remoção de um dependente.

* **Gestão de Cobranças e Pagamentos:**
    * `POST /cobrancas/gerar-mensal`: Geração automática das cobranças mensais para todos os associados.
    * `POST /cobrancas/{cobrancaId}/pagar`: Registro de um pagamento para uma cobrança específica.

* **Gestão de Reservas:**
    * `POST /reservas`: Criação de uma nova reserva para uma área do clube.
    * `GET /reservas`: Listagem de todas as reservas (ideal para painéis administrativos).
    * `GET /reservas/{id}`: Busca de uma reserva por ID.
    * `DELETE /reservas/{id}`: Cancelamento de uma reserva.

* **Inicialização de Dados:** O sistema popula automaticamente o banco com as áreas reserváveis do clube (churrasqueiras, salões, etc.) na primeira inicialização.

## Documentação da API (Swagger)

Toda a API está documentada e pode ser testada de forma interativa através do Swagger UI. Após iniciar a aplicação, acesse:

[**http://localhost:8080/swagger-ui.html**](http://localhost:8080/swagger-ui.html)

## Pré-requisitos para Execução

Para rodar este projeto localmente, você precisará ter instalado:

* Java (JDK 17 ou superior)
* Maven 3.x
* MySQL Server

## Instalação e Execução

Siga os passos abaixo para configurar e executar o projeto:

**1. Clone o repositório:**
```bash
git clone [URL_DO_SEU_REPOSITORIO]
cd [NOME_DA_PASTA_DO_PROJETO]
```

**2. Crie o Banco de Dados:**
Execute o seguinte comando no seu cliente MySQL para criar o banco de dados que a aplicação usará:
```sql
CREATE DATABASE clube_db;
```

**3. Configure a Conexão:**
Abra o arquivo `src/main/resources/application.properties` e altere as seguintes propriedades com as credenciais do seu banco de dados MySQL:
```properties
# Altere com o seu usuário e senha do MySQL
spring.datasource.username=root
spring.datasource.password=sua_senha
```

**4. Execute a Aplicação:**
Você pode rodar o projeto de duas formas:

* **Via Maven (linha de comando):**
  ```bash
  mvn spring-boot:run
  ```

* **Via sua IDE (IntelliJ, Eclipse, VSCode):**
  Abra o projeto na sua IDE e execute a classe principal `ClubeApplication.java`.

A aplicação estará disponível em `http://localhost:8080`.

## Desenvolvido por

* [Seu Nome Completo Aqui]
