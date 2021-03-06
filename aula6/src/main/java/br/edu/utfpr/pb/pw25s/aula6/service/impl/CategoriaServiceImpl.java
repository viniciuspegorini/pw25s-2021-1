package br.edu.utfpr.pb.pw25s.aula6.service.impl;

import br.edu.utfpr.pb.pw25s.aula6.model.Categoria;
import br.edu.utfpr.pb.pw25s.aula6.repository.CategoriaRepository;
import br.edu.utfpr.pb.pw25s.aula6.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl extends CrudServiceImpl<Categoria, Long>  implements CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Override
	protected JpaRepository<Categoria, Long> getRepository() {
		return categoriaRepository;
	}

}
