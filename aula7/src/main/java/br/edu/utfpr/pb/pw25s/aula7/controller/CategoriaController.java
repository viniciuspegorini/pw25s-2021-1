package br.edu.utfpr.pb.pw25s.aula7.controller;

import br.edu.utfpr.pb.pw25s.aula7.model.Categoria;
import br.edu.utfpr.pb.pw25s.aula7.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;

	// exibir a página list
	@GetMapping
	public String list() {
		return "categoria/list";
	}

	// listar categorias na datatables
	@GetMapping("/datatables/data")
	public ResponseEntity<?> findAllDatatables(HttpServletRequest request) {
		return ResponseEntity.ok(categoriaService.findAll(request));
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid Categoria categoria, BindingResult result) {
		if ( result.hasErrors() ) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		categoriaService.save(categoria);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("{id}") // /categoria/25
	@ResponseBody // {id:1,nome:Cat 1}
 	public Categoria findOne(@PathVariable Long id) {
		return categoriaService.findOne(id);
	}

	@DeleteMapping("{id}") // /categoria/1
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			categoriaService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}






