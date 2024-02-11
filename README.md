# certification_nlw

Projeto desenvolvido no NLW, evento da Rocketseat, seguindo a trilha Java com a instrutora Daniele Leão. </br></br>
Esta é uma aplicacao de certificacao, onde o usuário pode realizar as seguintes operacoes: verificar se possui permissão para fazer a prova, buscar as questoes e alternativas de resposta, responder a prova e consultar o ranking com as melhores notas.</br>
A aplicacao foi desenvolvida em Java 17 utilizando o framework Spring Boot 3.2.2 e banco de dados PostgreSQL.</br></br>

## 📋 Pré-requisitos

- JDK Java 17
- Docker Desktop (para o banco de dados, nesse caso o postgresql)
- Para o desenvolvimento deste projeto foi utilizada a IDE VSCode (utilize a de sua preferência)
- a extensao Database Client (ou outro gerenciador de bancos de dados de sua preferencia)

## ⚙️ Executando a aplicacao

- Executar o comando docker-compose up -d 
- Executar a aplicacao 
- Na primeira execucao e sempre que apagar as tabelas questions e alternatives, com a aplicacao em execucao, executar a classe CreateSeed. Isso vai fazer com que as tabelas de alternativas e questoes sejam criadas e carregadas com os dados contidos no arquivo create.sql 

## Exemplos de requisicoes

[Exemplos de requisicoes] (https://github.com/tutuia/certification_nlw/imagens)
