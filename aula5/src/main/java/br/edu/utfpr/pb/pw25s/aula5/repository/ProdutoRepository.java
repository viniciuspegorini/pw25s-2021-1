package br.edu.utfpr.pb.pw25s.aula5.repository;

import br.edu.utfpr.pb.pw25s.aula5.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

    List<Produto> findAllByCategoriaId(Long id);

}












