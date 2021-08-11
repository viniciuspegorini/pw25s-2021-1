package br.edu.utfpr.pb.pw25s.aula7.controller;

import br.edu.utfpr.pb.pw25s.aula7.model.Marca;
import br.edu.utfpr.pb.pw25s.aula7.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("marca")
public class MarcaController {
	
	@Autowired
	private MarcaService marcaService;
	
	@GetMapping
	public String list() {
		return "marca/list";
	}

	@GetMapping("data")
	@ResponseBody
	public List<Marca> findAll() {
		return marcaService.findAll();
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid Marca marca, BindingResult result) {
		if ( result.hasErrors() ) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		marcaService.save(marca);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("{id}") // /categoria/25
	@ResponseBody
	public Marca findOne(@PathVariable Long id) {
		return marcaService.findOne(id);
	}

	@DeleteMapping("{id}") // /categoria/1
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			marcaService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}





