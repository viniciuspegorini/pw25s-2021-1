package br.edu.utfpr.pb.pw25s.aula6.controller;

import br.edu.utfpr.pb.pw25s.aula6.model.Categoria;
import br.edu.utfpr.pb.pw25s.aula6.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	public String list() {
		return "categoria/list";
	}

	@GetMapping("data")
	@ResponseBody
	//[{id:1,nome:Cat 1},{id:1,nome:Cat 1},.. ]
	public List<Categoria> findAll() {
		return categoriaService.findAll();
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






