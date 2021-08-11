package br.edu.utfpr.pb.pw25s.aula7.service.impl;

import br.edu.utfpr.pb.pw25s.aula7.datatables.Datatables;
import br.edu.utfpr.pb.pw25s.aula7.datatables.DatatablesColumns;
import br.edu.utfpr.pb.pw25s.aula7.model.Categoria;
import br.edu.utfpr.pb.pw25s.aula7.repository.CategoriaRepository;
import br.edu.utfpr.pb.pw25s.aula7.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class CategoriaServiceImpl extends CrudServiceImpl<Categoria, Long>  implements CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private Datatables datatables;

	@Override
	protected JpaRepository<Categoria, Long> getRepository() {
		return categoriaRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> findAll(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColumns(DatatablesColumns.CATEGORIAS);
		Page<Categoria> page = datatables.getSearch().isEmpty()
				? categoriaRepository.findAll(datatables.getPageable())
				: categoriaRepository.findByNome(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}
}
