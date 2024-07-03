# Fórum Hub

## Tecnologias Utilizadas

[Bruno](https://www.usebruno.com/) - Cliente HTTP para desenvolver e testar rotas

[Spring Boot 3](https://start.spring.io/) - Framework Java para Backend

[PostgreSQL](https://www.postgresql.org/) - Banco de Dados

[Flyway](https://flywaydb.org/) - Migração de Banco de Dados

[Lombok](https://projectlombok.org/) - Gerador de Boilerplate

[Springdoc](https://springdoc.org) - Documentação simples e interativa

[Java JWT](https://github.com/auth0/java-jwt) - Autorização

## Rotas da Aplicação
`/users` - Gerenciamento de Usuários

`/courses` - Gerenciamento de Cursos

`/topics` - Gerenciamento de Tópicos

`/replies` - Gerenciamento de Respostas

`/login` - Login

Para mais informações de como utilizar as rotas é só acessar a documentação embutida: http://localhost:8080/swagger-ui.html

## Permissões

Para todas as rotas `GET`, não é necessário estar logado.

As rotas `POST /login` e `POST /users` também não precisa estar logado. Afinal, uma é para entrar e a outra para cadastrar.

Todas as rotas de gerenciamento dos cursos é necessário estar logado e ter permissão de administrador.

Apenas o próprio usuário ou um outro usuário, com permissão de administrador, podem alterar ou deletar um usuário.

Apenas usuários logados podem criar tópicos ou respostas.

Apenas o próprio autor ou um outro usuário, com permissão de administrador, podem alterar ou deletar um tópico ou resposta.

Apenas o autor do tópico ou um outro usuário, com permissão de administrador, podem marcar uma resposta como a solução do tópico.