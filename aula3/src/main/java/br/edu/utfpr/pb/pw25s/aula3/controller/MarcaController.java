package br.edu.utfpr.pb.pw25s.aula3.controller;

import br.edu.utfpr.pb.pw25s.aula3.model.Marca;
import br.edu.utfpr.pb.pw25s.aula3.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("marca")
public class MarcaController {
	
	@Autowired
	private MarcaService marcaService;
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("marcas", marcaService.findAll());
		return "marca/list";
	}

}






