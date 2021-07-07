## 1. Introdução
Neste projeto será discutido **como utilizar Thymeleaf com Spring**  juntamento com um caso de uso na camada de visão (*view*) de uma aplicação Spring Boot [1], Spring Web MVC [2], Spring Data JPA [3] e Thymeleaf [4].

### 1.1 Dependências do projeto.
O projeto será do tipo **Maven Project**.
A linguagem será **Java**.
A versão do Spring Boot será a versão estável atual na data de criação do projeto (**2.2.5**).
Os metadados do projeto são:
- Group: **br.edu.utfpr.pb.pw25s**
- Artifact: **aula3**
- Options:
    - Packaging: **Jar**
    - Java: **11**.

Em dependências devem ser selecionados os *frameworks*:
- Spring Web
- Spring Data JPA
- Spring Devtools
- PostgreSQL Driver (ou o driver do banco de sua preferência H2, MySQL, etc...)
- Lombok

### 1.2 Instruções para executar o projeto.
Como o projeto foi desenvolvido com Maven e Spring Boot o processo para executar o processo é bem simples. Inicialmente é necessário importar o projeto na IDE. O próximo passo será atualizar as dependências. No passo seguinte deve ser configurada a conexão com o banco de dados, por padrão foi criada uma conexão com o `PostgreSql`. O nome do banco de dados é `pw25s-aula3`, então basta criar o banco no Sistema Gerenciador de Banco de Dados (SGBD) e configurar o usuário e senha do banco de dados no arquivo `aplication.properties` que fica dentro de `src/main/resources`.

## Referências

[1] Spring Boot [https://docs.spring.io/spring-boot/docs/current/reference/html/](https://docs.spring.io/spring-boot/docs/current/reference/html/) *Acessado em: 05/07/2021*

[2] Spring Web MVC [https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc) *Acessado em: 05/07/2021*

[3] Spring Data JPA [https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#preface](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#preface) *Acessado em: 05/07/2021*

[4] Thymeleaf [https://www.thymeleaf.org/](https://www.thymeleaf.org/) - *Acessado em: 05/07/2021*

[5] W3Schools HTML [https://www.w3schools.com/html/](https://www.w3schools.com/html/) - *Acessado em: 05/07/2021*

[6] Spring Web MVC https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html *Acessado em: 05/07/2021*

# Atividade

Criar um novo projeto utilizando o mapeamento ORM das classes criadas no projeto anterior
- Cidade {id, nome}
- Autor {id, nome}
- Editora {id, nome}
- Categoria {id, descricao}
- Livro {id, titulo, EDITORA, GENERO, AUTOR, ano, isbn, CIDADE, valor}

E criar as telas de listagem e formulário para as classes sem relacionamento (Cidade, Autor, Editora, Categoria e Livro).