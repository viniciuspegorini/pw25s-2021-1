package br.edu.utfpr.pb.pw25s.aula7.service.impl;

import br.edu.utfpr.pb.pw25s.aula7.model.Fornecedor;
import br.edu.utfpr.pb.pw25s.aula7.repository.FornecedorRepository;
import br.edu.utfpr.pb.pw25s.aula7.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class FornecedorServiceImpl extends CrudServiceImpl<Fornecedor, Long>  implements FornecedorService {

	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@Override
	protected JpaRepository<Fornecedor, Long> getRepository() {
		return fornecedorRepository;
	}

}
