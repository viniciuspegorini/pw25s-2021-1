# Spring MVC + Javascript + Ajax

## 1. Introdução
No conteúdo do projeto são abordados conceitos de **JavaScript** e **Ajax** e **POST com JSON** em conjunto com o *framework* **Spring MVC**.

### 1.1 Dependências do projeto.
O projeto será do tipo **Maven Project**.
A linguagem será **Java**.
A versão do Spring Boot será a versão estável atual na data de criação do projeto (**2.2.5**).
Os metadados do projeto são:
- Group: **br.edu.utfpr.pb.pw25s**
- Artifact: **aula6**
- Options:
    - Packaging: **Jar**
    - Java: **11** ou superior.

As dependência utilizadas são as mesmas utilizadas no projeto **aula5**.

### 1.2 Instruções para executar o projeto.
Como o projeto foi desenvolvido com Maven e Spring Boot o processo para executar o processo é bem simples. Inicialmente é necessário importar o projeto na IDE. O próximo passo será atualizar as dependências. No passo seguinte deve ser configurada a conexão com o banco de dados, por padrão foi criada uma conexão com o `PostgreSql`. O nome do banco de dados é `pw25s_aula5`, então basta criar o banco no Sistema Gerenciador de Banco de Dados (SGBD) e configurar o usuário e senha do banco de dados no arquivo `aplication.properties` que fica dentro de `src/main/resources`.

## 2. Ajax

AJAX é um acrônimo de *Asynchronous JavaScript and XML* e é uma técnica de desenvolvimento web que possibilita criar aplicações com mais recursos de interatividade por meio de chamadas assíncronas à um servidor remoto. Geralmente implementado por meio da linguagem JavaScript. O JavaScript é uma das mais populares linguagens de programação do mundo.  É uma linguagem de programação interpretada. Desenvolvedores web devem aprender HTML, CSS e **Javascript**. A linguagem JavaScript vem evoluindo desde os anos 90, o JavaScript original ES1 ES2 ES3 (1997-1999), a primeira grande revisão é o ES5 (2009), a segunda revisão é o ES6 (2015) e novas atualizações ocorreram em 2016, 2017 e 2018 [1], [2].

Por meio de uma chamada AJAX é possível:
- Atualizar uma página web com dados do servidor sem precisar recarregá-la.
- Realizar requisições à um servidor web depois da página estar carregada.
- Receber requisições de um servidor web depois da página estar carregada.
- Enviar dados à um servidor em segundo plano.

Uma requisição utilizando Ajax ocorre por meio do objeto XMLHttpRequest, o qual define uma API que fornece funcionalidades para scripts do lado do cliente para transferência de dados entre o navegador e um servidor. O objeto suporta qualquer formato baseado em texto, por exemplo, o XML e JSON e permite fazer requisições ao servidor Web usando os protocolos HTTP e HTTPS.

Um exemplo de chamada assíncrona à um recurso no servidor pode ser observado na listagem abaixo:

```Javascript
function executeAjax() {  
	var xhttp = new XMLHttpRequest();  
	xhttp.onreadystatechange = function() {  
		if (this.readyState == 4 && this.status == 200) {  
			document.getElementById("demo").innerHTML = this.responseText;  
		}
	};  
	xhttp.open("GET", "/content", true);  
	xhttp.send();  
}
```

Na função JavaScript `executeAjax()`  inicialmente é instanciado um objeto **`XMLHttpRequest`** **xhttp**, então utilizamos esse objeto para tratar as mudanças de estado da requisição (*onreadystatechange*). Depois temos uma verificação da propriedade `readyState`, na qual o valor **4** indica que a requisição foi finalizada. E, também a verificação do valor da propriedade `status`, na qual o valor **200** significa sucesso. Na linha de código seguinte `document.getElementById("demo").innerHTML = this.responseText;` o elemento com identificador *demo* será atualizado com o valor vindo do servidor.
Na sequência temos `xhttp.open("GET", "/content", true);`, nesse trecho finalizamos a configuração da requisição, passando o método HTTP utilizado, a URL que será acessada no servidor e se a chamdada será assíncrona (**true**) ou síncrona (**false**). Por fim, a requisição é realizada executando o método `send()` **`xhttp.send();`**.