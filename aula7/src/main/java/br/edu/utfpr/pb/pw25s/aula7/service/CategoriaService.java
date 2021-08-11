package br.edu.utfpr.pb.pw25s.aula7.service;

import br.edu.utfpr.pb.pw25s.aula7.model.Categoria;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface CategoriaService extends CrudService<Categoria, Long>{

    Map<String, Object> findAll(HttpServletRequest request);
}
