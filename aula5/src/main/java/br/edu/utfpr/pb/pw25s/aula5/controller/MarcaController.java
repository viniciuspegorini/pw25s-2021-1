package br.edu.utfpr.pb.pw25s.aula5.controller;

import br.edu.utfpr.pb.pw25s.aula5.model.Marca;
import br.edu.utfpr.pb.pw25s.aula5.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


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
	
	@GetMapping(value = {"new", "novo", "form"})
	public String form(Model model) {
		model.addAttribute("marca", new Marca());
		return "marca/form";
	}

	@PostMapping
	public String save(@Valid Marca marca,
					   BindingResult result,
					   Model model,
					   RedirectAttributes attributes) {
		if ( result.hasErrors() ) {
			model.addAttribute("marca", marca);
			return "marca/form";
		}
		
		marcaService.save(marca);
		attributes.addFlashAttribute("sucesso", 
									 "Registro salvo com sucesso!");
		return "redirect:/marca";
	}

	@GetMapping("{id}") // /marca/25
 	public String form(@PathVariable Long id, Model model) {
		model.addAttribute("marca", marcaService.findOne(id));
		return "marca/form";
	}
	
	@DeleteMapping("{id}") // /marca/25
	public String delete(@PathVariable Long id,
						 RedirectAttributes attributes) {
		try {
			marcaService.delete(id);
			attributes.addFlashAttribute("sucesso", 
					"Registro removido com sucesso!");
		} catch (Exception e) {
			attributes.addFlashAttribute("erro", 
					"Falha ao remover o registro!");
		}
		return "redirect:/marca";
	}
}






