package br.edu.utfpr.pb.pw25s.aula2;

import br.edu.utfpr.pb.pw25s.aula2.model.Categoria;
import br.edu.utfpr.pb.pw25s.aula2.model.Produto;
import br.edu.utfpr.pb.pw25s.aula2.repository.CategoriaRepository;
import br.edu.utfpr.pb.pw25s.aula2.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication()
public class Aula2Application implements CommandLineRunner {

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	ProdutoRepository produtoRepository;

	public static void main(String[] args) {
		SpringApplication.run(Aula2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria c1 = new Categoria();
		c1.setNome( "Eletrônicos" );

		salvarCategoria(c1);

		c1.setNome("Eletrônico");
		salvarCategoria(c1);

		listarCategorias();

		Categoria c2 = new Categoria();
		c2.setNome( "Games" );
		salvarCategoria(c2);

		listarCategorias();
		removerCategoria(c2);
		listarCategorias();

		Produto p1 = new Produto();
		p1.setNome("Age of Empires");
		p1.setDescricao("Jogo desenvolvido de estratégia desenvolvido...");
		p1.setValor(99.9);
		p1.setCategoria( categoriaRepository.findById(1L).orElse(null) );
		p1.setDataFabricacao( LocalDate.now() );
		salvarProduto(p1);

		listarProdutos();

		System.out.println("*** Categoria findByNomeContains");
		categoriaRepository.findByNomeContains("a").forEach(c -> System.out.println(c));

		System.out.println("*** Produtos findByNomeContainsOrDescricaoContains");
		produtoRepository.findByNomeContainsOrDescricaoContains(
				"Refrigerador", "teclado").forEach(p -> System.out.println(p));

		System.out.println("*** Produtos findByCategoriaIdQueryNativa");
		produtoRepository.findByCategoriaIdQueryNativa(1L).forEach(
				obj -> System.out.println("Produto: " + obj[0] + " - " + obj[1] + " - " + obj[2])
		);

	}

	private void salvarProduto(Produto p1) {
		produtoRepository.save(p1);

		System.out.println("**** Produto " + p1 + " salvo com sucesso!");
	}

	private void removerCategoria(Categoria c2) {
		categoriaRepository.deleteById(c2.getId());
	}

	private void salvarCategoria(Categoria c1) {
		categoriaRepository.save(c1);

		System.out.println("**** Categoria " + c1 + " salva com sucesso!");
	}

	private void listarCategorias() {
		System.out.println("**** Lista de Categorias");
		categoriaRepository.findAll().forEach(c -> System.out.println(c));
	}

	private void listarProdutos() {
		System.out.println("**** Lista de Produtos");
		produtoRepository.findAll().forEach(p -> System.out.println(p));
	}


}
