# Spring MVC + Javascript + Ajax + Paginação de Dados

## 1. Introdução
No conteúdo do projeto são abordados conceitos de paginação de dados com **JavaScript** e **Ajax** em conjunto com o *framework* **Spring MVC** e o motor de template **Thymeleaf**. Além da paginação via javascript também será abordada a paginação com diretamente com o Thymeleaf.

### 1.1 Dependências do projeto.
O projeto será do tipo **Maven Project**.  
A linguagem será **Java**.  
A versão do Spring Boot será a versão estável atual na data de criação do projeto (**2.2.5**).  
Os metadados do projeto são:
- Group: **br.edu.utfpr.pb.pw25s**
- Artifact: **aula7**
- Options:
  - Packaging: **Jar**
- Java: **11** ou superior.

As dependência utilizadas são as mesmas utilizadas no projeto **aula6**. Com a adição das dependências do **datatables**[4] que é uma biblioteca que permite adicionar tabelas html com javascript e ajax.
```xml
<!-- Datatables -->  
<dependency>  
	<groupId>org.webjars</groupId>  
	<artifactId>datatables</artifactId>  
	<version>1.10.25</version>  
</dependency>  
<dependency>  
	<groupId>org.webjars</groupId>  
	<artifactId>datatables-responsive</artifactId>  
	<version>2.2.7</version>  
</dependency>  
<dependency>  
	<groupId>org.webjars</groupId>  
	<artifactId>momentjs</artifactId>  
	<version>2.29.1</version>  
</dependency>
```


### 1.2 Instruções para executar o projeto.
Como o projeto foi desenvolvido com Maven e Spring Boot o processo para executar o processo é bem simples. Inicialmente é necessário importar o projeto na IDE. O próximo passo será atualizar as dependências. No passo seguinte deve ser configurada a conexão com o banco de dados, por padrão foi criada uma conexão com o `PostgreSql`. O nome do banco de dados é `pw25s_aula7`, então basta criar o banco no Sistema Gerenciador de Banco de Dados (SGBD) e configurar o usuário e senha do banco de dados no arquivo `aplication.properties` que fica dentro de `src/main/resources`.

## 2. Prática

### 2.1 Paginação com a biblioteca datables.net utilizando Ajax

Após importados as dependências no `pom.xml`, deverão ser adicionadas as dependências de **css** e **javascript** na página com o **layout** da aplicação, conforme apresentado abaixo:
```html
<!-- CSS -->
<link rel="stylesheet" th:href="@{/webjars/datatables/1.10.25/css/dataTables.bootstrap5.min.css}"/>  
<link rel="stylesheet" th:href="@{/webjars//datatables-responsive/2.2.7/css/responsive.dataTables.min.css}"/>

<!-- JS -->
<script type="text/javascript" th:src="@{/webjars/momentjs/2.29.1/min/moment-with-locales.min.js}"></script>  
<script type="text/javascript" th:src="@{/webjars/datatables/1.10.25/js/jquery.dataTables.min.js}"></script>  
<script type="text/javascript" th:src="@{/webjars/datatables/1.10.25/js/dataTables.bootstrap5.min.js}"></script>  
<script type="text/javascript" th:src="@{/webjars/datatables-responsive/2.2.7/js/dataTables.responsive.min.js}"></script>
```
O próximo passo é configurar o lado servidor da aplicação, para isso serão criadas duas classes no pacote **datatables**: `Datatables` e `DatatablesColumns`. Também serão utilizadas para realizar a requisição as classes `CategoriaRepository`, `CategoriaService`, `CategoriaServiceImpl` e `CategoriaController`.

A classe `Datatables` será utilizada para receber os dados vindos da biblioteca datatables do lado cliente e também pra montar a resposta. O método `getPageable()` monta por meio dos dados vindos do cliente o objeto `Pageable` que será utilizado para realizar a consulta na base de dados e o método `getResponse()`  retorna a responsta ao cliente que realizou a requisição.

```java
@Component  
public class Datatables {  
	private HttpServletRequest request;  
	private String[] columns;  
	
	public Datatables() {}  
     
	public Map<String, Object> getResponse(Page<?> page) {      
	    Map<String, Object> json = new LinkedHashMap<>();  
		json.put("draw", draw());  
		json.put("recordsTotal", page.getTotalElements());  
		json.put("recordsFiltered", page.getTotalElements());  
		json.put("data", page.getContent());  
		return json;  
	}    
	public HttpServletRequest getRequest() {  
	    return request;  
	}  
	public void setRequest(HttpServletRequest request) {  
	    this.request = request;  
	}  
	public String[] getColumns() {  
      return columns;  
	}  
	public void setColumns(String[] columns) {  
	    this.columns = columns;  
	}  
	private int draw() {  
	    return Integer.parseInt(this.request.getParameter("draw"));  
	}   
	private int start() {  
	    return Integer.parseInt(this.request.getParameter("start"));  
	}   
	public int getLength() {  
	    return Integer.parseInt(this.request.getParameter("length"));  
	}  
	public int getCurrentPage() {  
	    return start() / getLength();  
	}  
	public String getColumnName() {  
	   int iCol = Integer.parseInt(this.request.getParameter("order[0][column]"));  
	   return this.columns[iCol];  
	}    
	public Sort.Direction getDirection() {  
	    String order = this.request.getParameter("order[0][dir]");  
		Sort.Direction sort = Sort.Direction.ASC;  
		if (order.equalsIgnoreCase("desc")) {  
	        sort = Sort.Direction.DESC;  
		}  
		return sort;  
	}  
	public String getSearch() {         
	    return this.request.getParameter("search[value]");  
	}   
	public Pageable getPageable() {  
	    return PageRequest.of(getCurrentPage(), getLength(), getDirection(), getColumnName());  
	}  
}
```
Já a classe `DatatablesColumns ` contém um *array* de *strings* colunas que serão retornadas ao cliente que realizou a requisição.

```java
public class DatatablesColumns {  
   public static final String[] CATEGORIAS = {"id", "nome"};  
}
```
Na classe `CategoriaRepository` foi criada a consulta `findByNome()`.
```java
	...
	@Query("select distinct c from Categoria c where c.nome like :search%")  
	Page<Categoria> findByNome(String search, Pageable pageable);
	...
```

Na classe `CategoriaService` foi criado a assinatura do método `findAll`.
```java
	Map<String, Object> findAll(HttpServletRequest request);
```

Na classe `CategoriaServiceImpl` foi criado o método `findAll()`, nesse método é criado um objeto `Page<Categoria>` de acordo com os dados vindos do lado cliente. Caso o usuário tenha realizado uma consulta filtrando pelo **nome** da **categoria** é chamado o método `findByNome()` da classe `CategoriaRepository`, caso contrário é chamado o método `findAll()`.
```java
	...
	@Override  
	@Transactional(readOnly = true)  
	public Map<String, Object> findAll(HttpServletRequest request) {  
		datatables.setRequest(request);  
		datatables.setColumns(DatatablesColumns.CATEGORIAS);  
		Page<Categoria> page = datatables.getSearch().isEmpty()  
	        ? categoriaRepository.findAll(datatables.getPageable())  
			: categoriaRepository.findByNome(datatables.getSearch(),
			datatables.getPageable());  
		return datatables.getResponse(page);  
	}
```
Por fim, na classe `CategoriaController` foram criados dois métodos, o `list()` que apenas retorna a página `list.html` que está dentro da pasta `categoria`. E o método `findAllDatatables()` que será responsável por devolver os dados para montar a tabela no lado cliente.
```java
	// exibir a página list  
	@GetMapping  
	public String list() {  
		return "categoria/list";  
	}  
	// listar categorias na datatables  
	@GetMapping("/datatables/data")  
	public ResponseEntity<?> findAllDatatables(HttpServletRequest request) {  
		return ResponseEntity.ok(categoriaService.findAll(request));  
	}
```
Agora serão exibidos os códigos dos arquivos `list.html` e `crud-categoria.js`. No arquivo HTML foi criada uma tabela com o id `table-data`. Essa tabela será utilizada no arquivo javascript para que a mesma seja populada com os dados vindos do **controller**.

```html
	...
	<table id="table-data" class="table table-sm table-striped table-bordered nowrap" style="width: 100%">  
		<thead class="thead-dark">  
			<tr>
				<th scope="col">#</th>  
				<th scope="col">Nome</th>  
				<th scope="col">Editar</th>  
				<th scope="col">Remover</th>  
			</tr>
		</thead>
	</table>
	...	
```

O arquivo `crud-categoria.js` possui o método `loadTable()`, no qual por meio da biblioteca `datatables` é criada a tabela `$('#table-data').DataTable({});`. Nessa tabela é informado o arquivo de tradução para os rótulos da tabela, se vai ter campo de busca e outras configurações, conforme comentários no código.

```js
$(document).ready(function() {  
    loadTable();  
});  
 
function loadTable() {  
	moment.locale('pt-BR');  
	var table = $('#table-data').DataTable({  
		"language": {  // tradução dos rótulos.
           "url": "//cdn.datatables.net/plug-ins/1.10.25/i18n/Portuguese-		Brasil.json"  
		},  
		searching : true, // vai ter campo de busca 
		lengthMenu : [ 5, 10 ], // tamanho das páginas  
		processing : true,  // indicador de profresso
		serverSide : true,  // vai buscar os dados no lado servidor
		responsive : true,  // responsivo
		ajax : {  // dados para carregar os dados do servidor
			url : '/categoria/datatables/data',  
			data : 'data'  
		},  
		columns : [  // configuração das colunas
			{data : 'id'},  
			{data : 'nome'},  
			{data : 'id',  
				render : function(id) {  
					return ''.concat('<a class="btn btn-warning btn-sm btn-block"', ' ')  
                       .concat('href="').concat('#"')  
					.concat('onclick="edit(').concat(id, ')"', ' ')  
					.concat('role="button" title="Editar" data-toggle="tooltip" data-placement="right">', ' ')  
					.concat('<i class="fa fa-edit"></i></a>');  
				},  
				orderable : false  
			},  
		  {  data : 'id',  
				render : function(id) {  
					return ''.concat('<a class="btn btn-danger btn-sm btn-block"', ' ')  
                        .concat('id="row_').concat(id).concat('"', ' ')  
                        .concat('onclick="remove(').concat(id, ')"', ' ')  
                        .concat('role="button" title="Remover" data-toggle="tooltip" data-placement="right">', ' ')  
                        .concat('<i class="fa fa-trash"></i></a>');  		
				},  
				orderable : false  
			}  
		]  
   });  
}
```

### 2.2 Paginação com Thymeleaf

A paginação com **Thymeleaf** também vai buscar os dados no lado servidor, entretanto, será carregada toda a página novamente a cada requisição. A classe controller utizada nesse exemplo foi a `ProdutoController`, conforme método apresentado abaixo:

```java
   ...
	@GetMapping  
	public String list(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, Model model) {  
		int currentPage = page.orElse(1);  
		int pageSize = size.orElse(5);  
		Page<Produto> list = this.produtoService.findAll(PageRequest.of(currentPage - 1, pageSize));  
		model.addAttribute("list", list);  
		
		if (list.getTotalPages() > 0) {  
			List<Integer> pageNumbers = IntStream.rangeClosed(1, list.getTotalPages()).boxed().collect(Collectors.toList());  
			model.addAttribute("pageNumbers", pageNumbers);  
		}  
		return "produto/list";  
}
...
```
Nesse trecho de código é possível observar que retornamos dois objetos com o **`model`**, o `list` que contém um `Page<Produto>` e o objeto `pageNumbers` que contém uma lista de inteiros com os números das páginas, que serão utilizados para montar a paginação no HTML.

A tabela do arquivo `list.html` da pasta `produto` é a mesma utilizada nos outros exemplos, agora percorremos o objeto `list` para exibir cada produto na tabela. A diferença está apenas após a tabela, onde é exibido a paginação. O objeto `pageNumbers` é utilizado para montar a lista de páginas com o link para chamada de cada página no lado servidor. Os atributos `size` e `page` são adicionados na URL, dessa maneira o endereço `.../produto?size=5&page=2` irá dividir os produtos de **5 em 5**, e irá exibir a página **2**.
```html
<table class="table table-striped table-responsive-md">  
	<thead>
		<tr>
			<th>Código</th>  
			<th>Nome</th>  
			<th>Valor</th>  
			<th>Categoria</th>  
			<th>Marca</th>  
			<th>Editar</th>  
			<th>Remover</th>  
		</tr>
	</thead>
	<tbody>
		<tr th:each="produto : ${list}">  
			<td th:text="${produto.id}">código</td>  
			<td th:text="${produto.nome}">nome</td>  
			<td th:text="'R$ ' + ${#numbers.formatDecimal(produto.valor,3,'POINT',2,'COMMA')}"></td>  
			<td th:text="${produto.categoria.nome}"></td>  
			<td th:text="${produto.marca.nome}"></td>  
			<!-- EDITAR -->  
			<td><a th:href="@{/produto/{id}(id=${produto.id})}" class="btn btn-primary"><i class="fa fa-edit ml-2"></i></a> </td>  <!-- REMOVER -->  
			<td>
				<form th:method="delete" th:action="@{/produto/{id}?_method=DELETE(id=${produto.id})}">  
					<button type="submit" class="btn btn-danger"><i class="fa fa-trash ml-2"></i></button>
				</form>
			</td>
		</tr>
	</tbody>
</table> <!-- FIM TABLE -->  
<!-- INÍCIO PAGINAÇÃO -->
<nav aria-label="Page navigation example"> 
	<ul class="pagination" th:if="${list.totalPages > 0}">  
		<li th:each="pageNumber : ${pageNumbers}" class="page-item" th:classappend="${pageNumber==list.number + 1} ? active" >  
			<a class="page-link" th:href="@{/produto(size=${list.size}, page=${pageNumber})}" th:text="${pageNumber}"></a>  
		</li>
	</ul>
</nav> <!-- FIM PAGINAÇÃO -->
```


## Referências

[1] JavaScript [https://www.w3schools.com/js/](https://www.w3schools.com/js/) *Acessado em: 02/08/2021*

[2] JavaScript [https://www.w3schools.com/jsref/default.asp](https://www.w3schools.com/jsref/default.asp) *Acessado em: 02/08/2021*

[3] Ajax [https://www.w3schools.com/xml/ajax_intro.asp](https://www.w3schools.com/xml/ajax_intro.asp) *Acessado em: 02/08/2021*

[4] Datatables [https://datatables.net/](https://datatables.net/) *Acessado em: 10/08/2021*

