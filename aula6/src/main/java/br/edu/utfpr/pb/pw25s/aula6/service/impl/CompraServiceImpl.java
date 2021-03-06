package br.edu.utfpr.pb.pw25s.aula6.service.impl;

import br.edu.utfpr.pb.pw25s.aula6.model.Compra;
import br.edu.utfpr.pb.pw25s.aula6.repository.CompraRepository;
import br.edu.utfpr.pb.pw25s.aula6.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CompraServiceImpl extends CrudServiceImpl<Compra, Long>  implements CompraService {

	@Autowired
	private CompraRepository compraRepository;
	
	@Override
	protected JpaRepository<Compra, Long> getRepository() {
		return compraRepository;
	}

}
