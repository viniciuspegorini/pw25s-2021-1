package br.edu.utfpr.pb.pw25s.aula2.repository;

import br.edu.utfpr.pb.pw25s.aula2.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Select * from Produto Where nome LIKE :nome
    List<Produto> findByNomeLike(String nome);

    // Select * from Produto Where nome LIKE %:nome% OR descricao LIKE %:descricao%
    // Contains = '%tes%' ; StartingWith = 'tes%'; EndingWith = '%tes'
    List<Produto> findByNomeContainsOrDescricaoContains(String nome, String descricao);

    // Select * from Produto Where nome LIKE %:nome% OR descricao LIKE %:descricao% Order By Valor DESC
    List<Produto> findByNomeContainsOrDescricaoContainsOrderByValorDesc(String nome, String descricao);

    // Select * from Produto Where  valor >= :valor Order By nome DESC
    List<Produto> findByValorGreaterThanEqualOrderByNomeDesc(Double valor);

    // Select * from Produto Inner Join Categoria on categoria.id = produto.categoria_id
    //    Where categoria.nome LIKE '%:nome%'
    List<Produto> findByCategoriaNomeContains(String categoria);

    Optional<List<Produto>> findByCategoriaId(Long id);

    //JPQL
    //Select p.* from Produto AS p Where p.nome like ':nome'
    @Query(value = "Select p from Produto p Where p.nome Like :nome")
    List<Produto> findByAlgumaCoisa(@Param("nome") String nome);

    //Select p.* from Produto AS p Where p.categoria.id = :categoriaId
    @Query(value = "Select p from Produto p Where p.categoria.id = :categoriaId")
    List<Produto> findByCategoriaIdQuery(@Param("categoriaId") Long categoriaId);

    // Query Nativa
    @Query(value = "Select p.id, p.nome, p.valor from produto p Where p.categoria_id = :categoriaId", nativeQuery = true)
    List<Object[]> findByCategoriaIdQueryNativa(@Param("categoriaId") Long categoriaId);


    List<Produto> findByDataFabricacaoBetween(LocalDate dataIni, LocalDate dataFim);
}
