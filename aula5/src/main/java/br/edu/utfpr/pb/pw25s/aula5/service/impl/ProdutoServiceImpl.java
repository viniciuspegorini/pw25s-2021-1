package br.edu.utfpr.pb.pw25s.aula5.service.impl;

import br.edu.utfpr.pb.pw25s.aula5.model.Produto;
import br.edu.utfpr.pb.pw25s.aula5.repository.ProdutoRepository;
import br.edu.utfpr.pb.pw25s.aula5.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl extends CrudServiceImpl<Produto, Long>  implements ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Override
	protected JpaRepository<Produto, Long> getRepository() {
		return produtoRepository;
	}

	@Override
	public List<Produto> findAllByCategoriaId(Long id) {
		return produtoRepository.findAllByCategoriaId(id);
	}
}
