## 1. Introdução
O conteúdo abordado no projeto é o conceito de **Template Layout** com **Thymeleaf**[1], ou seja, uma maneira de incluir em nossas páginas fragmentos de código de outros templates, tais como: menus, rodapés, entre outros. Também será trabalhado o conceito dependências utilizando **WebJars**[2].

### 1.1 Dependências do projeto.
O projeto será do tipo **Maven Project**.
A linguagem será **Java**.
A versão do Spring Boot será a versão estável atual na data de criação do projeto (**2.2.5**).
Os metadados do projeto são:
- Group: **br.edu.utfpr.pb.pw25s**
- Artifact: **aula4**
- Options:
    - Packaging: **Jar**
    - Java: **11** ou superior.

Em dependências devem ser selecionados as dependências via spring boot:
- Spring Web
- Spring Data JPA
- Spring Devtools
- PostgreSQL Driver (ou o driver do banco de sua preferência H2, MySQL, etc...)
- Lombok
- Thymeleaf
- Thymeleaf extras Java8Time
- Thymeleaf Layout Dialect

Outras dependências que deverão ser adicionadas no arquivo pom.xml:
``` xml
<dependency>  
	<groupId>org.webjars</groupId>  
	<artifactId>webjars-locator</artifactId>  
	<version>0.30</version>  
</dependency>  
<dependency>  
	<groupId>org.webjars</groupId>  
	<artifactId>jquery</artifactId>  
	<version>3.6.0</version>  
</dependency>  
<dependency>  
	<groupId>org.webjars</groupId>  
	<artifactId>bootstrap</artifactId>  
	<version>4.6.0</version>  
</dependency>  
<dependency>  
	<groupId>org.webjars.npm</groupId>  
	<artifactId>sweetalert2</artifactId>  
	<version>10.15.5</version>  
</dependency>  
<dependency>  
	<groupId>org.webjars.bower</groupId>  
	<artifactId>bootstrap-datepicker</artifactId>  
	<version>1.9.0</version>  
</dependency>  
<dependency>  
	<groupId>org.webjars.bower</groupId>  
	<artifactId>fontawesome</artifactId>  
	<version>4.7.0</version>  
</dependency>  
<dependency>  
	<groupId>org.webjars.bower</groupId>  
	<artifactId>parsleyjs</artifactId>  
	<version>2.8.1</version>  
</dependency>
```

### 1.2 Instruções para executar o projeto.
Como o projeto foi desenvolvido com Maven e Spring Boot o processo para executar o processo é bem simples. Inicialmente é necessário importar o projeto na IDE. O próximo passo será atualizar as dependências. No passo seguinte deve ser configurada a conexão com o banco de dados, por padrão foi criada uma conexão com o `PostgreSql`. O nome do banco de dados é `pw25s-aula4`, então basta criar o banco no Sistema Gerenciador de Banco de Dados (SGBD) e configurar o usuário e senha do banco de dados no arquivo `aplication.properties` que fica dentro de `src/main/resources`.

## 2. WebJars

**WebJars** são bibliotecas para o lado cliente de uma aplicação web (ex.: Bootstrap, JQuery, SweetAlert, etc.) adicionadas em um arquivo do tipo **Java Archive** (JAR).
Possui como vantagens:
- Gerenciar de maneira explícita e fácil as dependências do lado do cliente em aplicativos da web baseados em Java, tornando esse processo mais transparente;
- Permite utilizar ferramentas de compilação baseadas em JVM (por exemplo, Maven, Gradle, sbt, ...) para baixar suas dependências do lado do cliente
- Dependências transitivas são resolvidas automaticamente e carregadas opcionalmente via RequireJS
- Implantado no repositório Maven Central
- Possui *content delivery network* (CDN) público;

Para utilizar **WebJars** em uma aplicação web basta adicionar a sua dependência no arquivo **pom.xml**.
```xml
<dependency>  
	<groupId>org.webjars</groupId>  
	<artifactId>webjars-locator</artifactId>  
	<version>0.30</version>  
</dependency>  
```
Depois disso é possível adicionar as dependências do lado cliente que serão utilizdas na aplicação. Vamos adicionar a dependência do **Bootstrap**, o primeiro passo é buscar o XML que representa a dependência, que pode ser realizado no endereço: [https://www.webjars.org/](https://www.webjars.org/).
```xml
<dependency>  
	<groupId>org.webjars</groupId>  
	<artifactId>bootstrap</artifactId>  
	<version>4.6.0</version>  
</dependency>  
```
O próximo passo é adicionar as dependências nas páginas HTML, esse processo é semelhante a qualquer importação de CSS ou JavaScript externo em uma página, como pode ser observado no exemplo abaixo. O caminho para as dependências pode ser encontrado no próprio site [https://www.webjars.org/](https://www.webjars.org/).
```HTML
<!-- CSS -->
<link rel="stylesheet" th:href="@{/webjars/bootstrap/4.6.0/css/bootstrap.min.css}"/>
<!-- JavaScript -->
<script type="text/javascript" th:src="@{/webjars/bootstrap/js/bootstrap.min.js}"></script>
```
## 3.  Template Layout com Thymeleaf e Fragments

### 3.1 Template Layout
A função principal do uso de **Template Layout** com **Thymeleaf** é reaproveitar códigos HTML criados na aplicação web evitando repetição dos mesmos, como, por exemplo em cabeçalhos, rodapés, menus e outros componentes.

O primeiro passo é adicionar a dependência no arquivo **pom.xml**.
```XML
<dependency>  
	<groupId>nz.net.ultraq.thymeleaf</groupId>  
	<artifactId>thymeleaf-layout-dialect</artifactId>  
</dependency>
```
O próximo passo é configurar o suporte ao uso de templates. Uma das maneiras é declarando o *@Bean* **LayoutDialect** na classe principal da aplicação, anotada com *@SpringBootApplication*.
```Java
//... outros imports
import nz.net.ultraq.thymeleaf.LayoutDialect;  

@SpringBootApplication  
public class Aula4Application {
	//... códigos
	
	@Bean  
	public LayoutDialect layoutDialect() {  
	    return new LayoutDialect();  
	}
	
	//... códigos
}
```

Após realizada a configuração é possível utilizar o namespace _layout_  e 5 novos atributos:  _decorate_,  _title-pattern_,  _insert_,  _replace_, and  _fragment._
Para criar o **template** da aplicação será criado o arquivo HTML  _layout-padrao.html_, dentro do diretório _/resources/templates/layout/_, esse arquivo vai possuir agora o _namespace_ _**layout**_ (*xmlns:layout*).
```html
<!DOCTYPE html>  
<html lang="pt" xmlns="http://www.w3.org/1999/xhtml"  
  xmlns:th="http://www.thymeleaf.org"  
  xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout">
... <!-- restante do código HTML -->
 </html>
```

#### 3.1.1 *layout:fragment*

Para criar seções personalizadas no *layout* ou modelos reutilizáveis que podem ser substituídos por seções que compartilham o mesmo nome, utiliza-se o atributo ***fragment*** dentro do arquivo _layout-padrao.html_. O conteúdo dentro de _**layout:fragment="conteudo"**_ será substituído pelo conteúdo das demais páginas da aplicação.
```html
<body>
	<header>
		<h1>Cabeçalho da página...</h1>
	</header>
	<section layout:fragment="conteudo">
		<p>O conteúdo da página vai aqui...</p>  
	</section>
	<footer>
		<p>Rodapé da página...</p>		
	</footer>
</body>
```
A página abaixo é a _index.html_, que será a página inicial da aplicação. Ela irá utilizar o _layout_ padrão criado anteriormente por meio do _namespace_ _**layout:decorate**_ em: **layout:decorate="~{layout/layout-padrao}"**. O conteúdo anterior e posterior à seção **<section layout:fragment="conteudo">** dessa página não será exibido no lado cliente, esse conteúdo irá vir dá página _layout-padrao.html_.
```html
<!DOCTYPE html>  
<html lang="pt"  
  xmlns="http://www.w3.org/1999/xhtml"  
  xmlns:th="http://www.thymeleaf.org"  
  xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"  
  layout:decorate="~{layout/layout-padrao}">  
<head>  
</head>  
<body>  
	<section layout:fragment="conteudo">  
		<div class="container-fluid">  
			<div class="jumbotron">  
				<h1>Bem vindo.</h1>  
			</div>
		</div>
	</section>  
</body>  
</html>
```
### 3.2 Fragments: _th:fragment_
Na nossa aplicação web também podemos criar pequenos fragmentos de código com _**th:fragment**_. No exemplo abaixo será criado o menu da nossa aplicação. Para isso foi criado o arquivo  **_nav_bar.html_** em _/resources/templates/layout/fragments/_. Esse menu poderá ser adicionado no arquivo _layout-padrao.html_ e assim disponibilizado para as demais páginas da aplicação.
```html
<!DOCTYPE html>  
<html lang="pt" xmlns="http://www.w3.org/1999/xhtml"  
  xmlns:th="http://www.thymeleaf.org">  
  
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark" th:fragment="navbar">  
		 <a class="navbar-brand  hidden-xs" href="/"></a>
		 ...
	 </nav>
	 
 </html>
```
Para adicionar o menu no arquivo _layout-padrao.html_ basta utilizar o atributo _**th:fragment**_, como pode ser observado no exemplo abaixo. Em que *layout/fragments/nav-bar* é o nome do arquivo e *navbar* é o nome do fragmento.
```html
...
<body>
	<header>
		<div th:replace="layout/fragments/nav-bar :: navbar"></div>
	</header>
	<section layout:fragment="conteudo">
		<p>O conteúdo da página vai aqui...</p>  
	</section>
	<footer>
		<p>Rodapé da página...</p>		
	</footer>
</body>
...
```
O mesmo procedimento poderá ser realizado para o rodapé da página. Nesse caso foi criado o arquivo **_footer.html_** em _/resources/templates/layout/fragments/_.
```html
<!DOCTYPE html>  
<html lang="pt" xmlns="http://www.w3.org/1999/xhtml"  
  xmlns:th="http://www.thymeleaf.org">  
<footer class="footer mt-auto py-3 bg-light footer-mg" th:fragment="footer">  
	<div class="container">  
		<span>&copy; 2021 UTFPR. Todos os direitos reservados.</span>  
	</div>
</footer>  
</html>
```
Após criado o arquivo de rodapé o mesmo poderá ser utilizado no _layout-padrão.html_.
```html
...
<body>
	<header>
		<div th:replace="layout/fragments/nav-bar :: navbar"></div>
	</header>
	<section layout:fragment="conteudo">
		<p>O conteúdo da página vai aqui...</p>  
	</section>
	<footer>
		<div th:replace="layout/fragments/footer :: footer"></div>
	</footer>
</body>
...
```
### 3.2.3 Fragments: _th:fragment_ + _th:block_
O atributo ***th:block*** permite adicionar fragmentos de código sem necessáriamente estar incorporado em uma *tag* HTML. Na nossa aplicação esse atributo foi adicionado para criar fragmentos de CSS e JavaScript específicos de cada página da aplicação, como em **<th:block layout:fragment="css"></th:block>** .
```html
<!DOCTYPE html>  
<!DOCTYPE html>  
<html lang="pt" xmlns="http://www.w3.org/1999/xhtml"  
  xmlns:th="http://www.thymeleaf.org"  
  xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout">  
<head>
	...
    <th:block layout:fragment="css"></th:block>  
</head>
<body>
	...
	<th:block layout:fragment="javascript"></th:block>  
</body>  
</html>
```
E para utilizá-lo em uma página, podemos utilizar como no exemplo abaixo, criado na página *index.html*.
```html
<!DOCTYPE html>  
<html lang="pt"  
  xmlns="http://www.w3.org/1999/xhtml"  
  xmlns:th="http://www.thymeleaf.org"  
  xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"  
  layout:decorate="~{layout/layout-padrao}">  
<head>   
</head>  
<body>  
	<section layout:fragment="conteudo">  
		<div class="container-fluid">  
			<div class="jumbotron">  
				<h1>Bem vindo.</h1>  
			</div>
		</div>
	</section>  
	<th:block layout:fragment="javascript">  
		<script type="text/javascript">  
			function alerta() {  
	            window.alert('Hi Mundo!');  
			}  
		</script>  
	</th:block>  
</body>  
</html>
```


## Referências

[1] Thymeleaf [https://www.thymeleaf.org/documentation.html](https://www.thymeleaf.org/documentation.html) *Acessado em: 20/07/2021*
[2] WebJars [https://www.webjars.org/](https://www.webjars.org/) *Acessado em: 20/07/2021*
[3] FromHTMLtoHTML [https://www.thymeleaf.org/doc/articles/fromhtmltohtmlviahtml.html](https://www.thymeleaf.org/doc/articles/fromhtmltohtmlviahtml.html) *Acessado em: 20/07/2021*