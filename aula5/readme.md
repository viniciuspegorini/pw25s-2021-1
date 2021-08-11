# Spring Security

## 1. Introdução
O conteúdo abordado no projeto é o conceito de autenticação e autorização com o *framework* **Spring Security**[1]. Neste projeto será criada uma tela de autenticação e também serão configurados diferentes níveis de autorização dentro do sistema.

### 1.1 Dependências do projeto.
Será criado um projeto [Maven](https://maven.apache.org/) por meio da ferramenta web [Spring Initializr](https://start.spring.io/) com as seguintes configurações:
A linguagem será **Java**.
A versão do Spring Boot será a versão estável atual na data de criação do projeto (**2.2.5**).
Os metadados do projeto são:
- Group: **br.edu.utfpr.pb.pw25s**
- Artifact: **aula4**
- Options:
    - Packaging: **Jar**
    - Java: **11** ou superior.

Em dependências devem ser selecionados as dependências via Spring boot:
- Spring Web
- Spring Data JPA
- Spring Devtools
- PostgreSQL Driver (ou o driver do banco de sua preferência H2, MySQL, etc...)
- Lombok
- Thymeleaf
- Thymeleaf extras Java8Time
- Thymeleaf Layout Dialect
- **Spring Security**

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
Como o projeto foi desenvolvido com Maven e Spring Boot o processo para executar o processo é bem simples. Inicialmente é necessário importar o projeto na IDE. O próximo passo será atualizar as dependências. No passo seguinte deve ser configurada a conexão com o banco de dados, por padrão foi criada uma conexão com o `PostgreSql`. O nome do banco de dados é `pw25s_aula5`, então basta criar o banco no Sistema Gerenciador de Banco de Dados (SGBD) e configurar o usuário e senha do banco de dados no arquivo `aplication.properties` que fica dentro de `src/main/resources`.


## 2. O *Framework* Spring Security

O **Spring Security** é um *framework* de autenticação e controle de acesso  que nos permite personalizar quem acessará os diferentes recursos de um sistema. É *framework* o padrão para para autenticação e autorização em aplicações baseadas em Spring.

Algumas das características do Spring Security são:
- Possui um claro controle e customização dos processos de autenticação e autorização.

- Proteção contra ataques de: *session fixation*[2], *clickjacking*[3], *cross site request forgery*[4], XSS [5], entre outros.

- Integração com a Servlet API

- Integração com o *framework* Spring Web MVC.

O Spring Security não é um *framework* de segurança, mas sim um *framework* de autenticação de autorização. Ou seja, ele auxilía no controle de acesso do sistema, entretanto existem outros problemas que também devem ser tratados pelo desenvolvedor que não são resolvidos pelo Spring Security, como por exemplo, ataques de negação de serviço (DoS: *Denial of Service*)[6], *sql injection*[7] e outras ameaças que devem ser observadas durante o desenvolvimento de aplicações web.

### 2.1 Iniciando com o Spring security

Para o Spring Security ser habilitado em uma aplicação web basta adicionar a dependência no arquivo `pom.xml`. Ao iniciar uma aplicação com Spring Security todas os recursos da aplicação estarão protegidos por usuário e senha. Entretanto o Spring apenas gera um usuário padrão chamado `user` e uma senha aleatória que exibida no console de execução do projeto.

O próximo passo é configurar o Spring Security na aplicação, para isso será criada a classe `WebSecurityConfig` dentro do pacote `br.edu.utfpr.pb.pw25s.aula5.config`. Diferente dos valores padrão do Spring Security, neste projeto os dados de **nome de usuário** e **senha** serão armazenados no banco de dados, por isso é necessário informar para o *framework* qual será o *service* responsável por fazer a consulta de nome de usuário, nesse caso por meio de uma instância do objeto `UsuarioServiceImpl `, que será apresentado na sequência.

No método `configure(HttpSecurity http)` são realizadas as configurações de autenticação e autorização do projeto.

No método `configure(WebSecurity web)` estão informados os recursos que serão ignorados pelo Spring Security, nesse caso arquivos de CSS, JavaScript, imagens, etc.

Na sequência são definidos os métodos que retornam o *service* que será utilizado na autenticação e também o sistema de criptografia que foi utilizado para fazer o encode da senha que está armazenada no banco de dados (`userDetailsService()` e `passwordEncoder()` ). Por fim, esses dados são informados ao *framework* por meio do método `configure(AuthenticationManagerBuilder auth)`.

```Java
// imports (os imports podem ser visualizados no código-fonte do projeto
@EnableWebSecurity  
@EnableGlobalMethodSecurity(securedEnabled = true)  
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{  
  
   @Autowired  
  private UsuarioServiceImpl usuarioService;  
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {  
      http.csrf().disable() // disabilida o csrf durante o desenvolvimento 
         .exceptionHandling().accessDeniedPage("/403") // trata os erros redirecionando para url /403 
         .and().formLogin().loginPage("/login")  //informa ao framework que não será utilizada a página padrão para login, e sim uma URL /login, que será informada no LoginController
         .defaultSuccessUrl("/") // caso o usuário seja autenticado com sucesso será redirecionado para essa URL
         .failureUrl("/login?error=bad_credentials").permitAll()  // no caso de erro de usuário e senha
         .and().logout().logoutSuccessUrl("/login")  // ao realizar logout o usuário será redirecionado para página de login
         .and().authorizeRequests() // abaixo são definidas as regras de autorização
            .antMatchers("/categoria/**").hasAnyRole("USER", "ADMIN") // para acessar a URLs dentro de /categoria o usuário precisa ter permissão de USER ou ADMIN 
            .antMatchers("/marca/**").hasAnyRole("USER", "ADMIN")  
            .antMatchers("/produto/**").hasRole("ADMIN")  
            .antMatchers("/cadastro/**").permitAll() // qualquer usuário pode acessar a URL /cadastro 
            .antMatchers("/**").authenticated(); // para as demais URLs não configuradas o usuário precisa estar autenticado, independente das suas permissões.
  }  
     
  @Override  
  public void configure(WebSecurity web) throws Exception {  
      web.ignoring()  
            .antMatchers("/css/**")  
            .antMatchers("/js/**")  
            .antMatchers("/images/**")  
            .antMatchers("/assets/**")  
            .antMatchers("/vendors/**")  
            .antMatchers("/webjars/**");  
  }  
     
  @Bean  
  @Override  
  protected UserDetailsService userDetailsService() {  
      return usuarioService;  
  }  
     
  @Bean  
  protected PasswordEncoder passwordEncoder() {  
      return new BCryptPasswordEncoder();  
  }  
     
  @Override  
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {  
      auth.userDetailsService( userDetailsService() )  
          .passwordEncoder( passwordEncoder() );  
  }  
}
```
Como informado anteriormente, os dados necessários para autenticação e autorização serão armazenados no banco de dados, por isso será necessário criar o *service* `UsuarioServiceImpl`. Esse *service* segue o mesmo padrão do criado na aula de **Spring Data**, entretanto agora temos que implementar a *interface* `UserDetailsService`, pois é por meio dela que Spring Security será capaz de realizar a autenticação do usuário. Note que é necessário sobrescrever o método `loadUserByUsername(String username)`, que retorna uma instância do objeto `UserDetais`, que possui os métodos para retornar os atributos de nome de usuário (**username**) e senha (**password**). Nesse projeto também vamos criar uma classe para armazenar um Usuário customizado no banco de dados, essa classe será apresentada na sequência.

```Java
@Service  
public class UsuarioServiceImpl extends CrudServiceImpl<Usuario, Long>  
      implements UsuarioService, UserDetailsService{  
  
  @Autowired  
  private UsuarioRepository usuarioRepository;  

  @Override  
  protected JpaRepository<Usuario, Long> getRepository() {  
      return usuarioRepository;  
  }  
  
   @Override  
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  
      Usuario usuario = this.usuarioRepository.findByUsername(username);  
	  if (usuario == null) {  
	      throw new UsernameNotFoundException("Usuário não encontrado");  
	  }  
      return usuario;  
  }  
     
}
```

A classe `Usuario` será a responsável por fornecer instâncias dos objetos que representam os usuários do sistema, por isso, implementa a *interface* `UserDetails`. Ao realizar essa implementação alguns métodos deverão ser sobrescritos, e devemos observar principalmente os métos `getUsername()`, `getPassword()` e `getAuthorities()`. Esses métodos retornam o **nome de usuário**, a **senha** e a **lista de permissões** desse usuário, respectivamente. Alguns outros métodos também deverão ser sobrescritos, entretanto serão utilizados para controlar a situação do usuário armazenado, se ele está ativo, se as credênciais ainda são válidas, etc.

Observa-se que a lista de permissões irá retornar o atributo `Set<Permissao> permissoes`. A classe `Permissao` será utilizada pelo Spring Security para validar os níveis de acesso ao sistema. Na classe `WebSecurityConfig` as palavras **USER** e **ADMIN** utilizadas no código `.antMatchers("/categoria/**").hasAnyRole("USER", "ADMIN")` presente no método `configure(HttpSecurity http)` representam as permissões do usuário. A classe `Permissao` será apresentada abaixo.

```Java
@Entity  
@Data  
@AllArgsConstructor  
@NoArgsConstructor  
@EqualsAndHashCode(of = {"id"})  
public class Usuario implements Serializable, UserDetails{  
   private static final long serialVersionUID = 1L;  
  
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	private Long id;  
	@Column(length = 255, nullable = false)  
    private String nome;  
	@Column(length = 100, nullable = false)  
    private String username;  
	@Column(length = 1024, nullable = false)  
    private String password;  
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)  
    private Set<Permissao> permissoes;  
  
    @Override  
    public Collection<? extends GrantedAuthority> getAuthorities() {  
      List<GrantedAuthority> list = new ArrayList<>();   
      list.addAll(permissoes);  
	  return list;
	}  

	@Override public boolean isAccountNonExpired() { return true; }  
	@Override public boolean isAccountNonLocked() { return true; }  
	@Override public boolean isCredentialsNonExpired() { return true; }
	@Override public boolean isEnabled() { return true; }
}
```
Assim como nas classes anteriores a `Permissao` também implementa uma interface do Spring Security, nesse caso a `GrantedAuthority`. É necessário sobrescrever o método `getAuthority()` o qual irá retornar o nome da permissão do usuário, como pode ser observado anteriormente será retornado **ADMIN**, **USER** ou qualquer nome que o desenvolvedor queira criar para suas permissões. Essas permissões também poderiam ser armazenadas em um **ENUM**.

É **importante** observar que no banco de dados a permissão deve ser armazenada utilizando o prefixo **ROLE_** e depois o nome da permissão, ou seja: **ROLE_ADMIN**, **ROLE_USER**, etc. O Spring Security ignora o prefixo quando configuramos as prermissões na classe de configuração.

```Java
@Entity  
@Data  
@NoArgsConstructor  
@AllArgsConstructor  
@EqualsAndHashCode(of = {"id", "nome"})  
public class Permissao implements Serializable, GrantedAuthority{  
   private static final long serialVersionUID = 1L;  
  
   @Id  
   @GeneratedValue(strategy = GenerationType.IDENTITY)  
   private Integer id;  
   @Column(length = 20, nullable = false)  
   private String nome;  
  
   @Override  
   public String getAuthority() {  
      return this.nome;  
   }    
}
```


## Referências

[1] Spring Security [https://spring.io/projects/spring-security](https://spring.io/projects/spring-security) *Acessado em: 27/07/2021*
[2] Session Fixation [https://owasp.org/www-community/attacks/Session_fixation](https://owasp.org/www-community/attacks/Session_fixation) *Acessado em: 27/07/2021*
[3] Session Hijacking [https://owasp.org/www-community/attacks/Session_hijacking_attack](https://owasp.org/www-community/attacks/Session_hijacking_attack) *Acessado em: 27/07/2021*
[4] CSRF [https://owasp.org/www-community/attacks/csrf](https://owasp.org/www-community/attacks/csrf) *Acessado em: 27/07/2021*
[5] XSS [https://owasp.org/www-community/attacks/xss/](https://owasp.org/www-community/attacks/xss/) *Acessado em: 27/07/2021*
[6] DoS [https://owasp.org/www-community/attacks/Denial_of_Service](https://owasp.org/www-community/attacks/Denial_of_Service) *Acessado em: 27/07/2021*
[7] SQL Injection [https://owasp.org/www-community/attacks/SQL_Injection](https://owasp.org/www-community/attacks/SQL_Injection) *Acessado em: 27/07/2021*
