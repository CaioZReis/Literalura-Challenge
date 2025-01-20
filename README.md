# Literalura ONE
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)

Bem-vindo ao projeto **LiteraluraONE**! Esta API tem como objetivo de superar um desafio(Challenge) dado aos alunos do Projeto ONE.
Utilizando consumo da API **GUTENDEX**.

## Índice

- [Visão Geral](#visão-geral)
- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Como Usar](#como-usar)

## Visão Geral

O LiteraluraONE é um projeto que busca obras literárias pelo nome do livro ou nome do autor consumindo os dados da API GUTENDEX e salvando informações como, nome do livro, numero de donwloads, lingua escrita e outros dados do autor como nome, data de nascimento e data de falecimento num banco de dados Postgres na própria máquina do usuário.

## Funcionalidades

- **Buscar livros(consumindo API GUTENDEX):** Procura o nome do livro ou do autor na API.
- **Listar livros registrados:** Retorna todos os livros que o usuário procurou e salvou no banco de dados.
- **Listar autores registrados:** Retorna todos os autores que o usuário procurou e salvou no banco de dados.
- **Listar autores vivos em um determinado ano:** Retorna somente os autores vivos salvos no banco de dados até a data que o usuário determinou.
- **Listar livros em um determinado idioma:** Primeiramente lista quais idiomas existem no banco de dados e depois procura todos os livros referentes ao que o usuário inseriu.

## Tecnologias Utilizadas
<div align="center">

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

</div>

- **Java 21:** Linguagem utilizada.
- **Spring:** Framework.
- **Postgres:** Banco de dados relacional.

## Como Usar

1. Iniciando o projeto, o menu mostrará algumas opções e uma delas é a "Buscar e registrar livros pelo título (Gutendex API)" nela é possível inserir o nome do livro ou do autor para procurar um ou mais livros.
2. Logo depois se o programa encontrar somente um livro pergunta se ele está certo, estando, insere no banco, senão descarta a informação, se vier mais de uma opção de livro o usuário precisa escolher uma delas para gravar no banco de dados.
3. Depois é possível procurar todos os livros e autores inseridos no banco de dados ou até quais autores estão vivos até um determinado ano, e também listar os idiomas e procurar todos os livros do mesmo idioma.
