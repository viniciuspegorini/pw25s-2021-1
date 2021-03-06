package br.edu.utfpr.pb.pw25s.aula6.service.impl;

import br.edu.utfpr.pb.pw25s.aula6.model.Produto;
import br.edu.utfpr.pb.pw25s.aula6.repository.ProdutoRepository;
import br.edu.utfpr.pb.pw25s.aula6.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProdutoServiceImpl extends CrudServiceImpl<Produto, Long>  implements ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Override
	protected JpaRepository<Produto, Long> getRepository() {
		return produtoRepository;
	}

}
