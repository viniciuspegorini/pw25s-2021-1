package br.edu.utfpr.pb.pw25s.aula5.service;

import br.edu.utfpr.pb.pw25s.aula5.model.Produto;

import java.util.List;

public interface ProdutoService extends CrudService<Produto, Long>{

    List<Produto> findAllByCategoriaId(Long id);

}
