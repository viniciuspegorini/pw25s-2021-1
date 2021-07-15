package br.edu.utfpr.pb.pw25s.aula3.controller;

import br.edu.utfpr.pb.pw25s.aula3.model.Categoria;
import br.edu.utfpr.pb.pw25s.aula3.model.Produto;
import br.edu.utfpr.pb.pw25s.aula3.service.CategoriaService;
import br.edu.utfpr.pb.pw25s.aula3.service.MarcaService;
import br.edu.utfpr.pb.pw25s.aula3.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private MarcaService marcaService;

	@GetMapping
	public String list(Model model) {
		model.addAttribute("produtos", produtoService.findAll());
		return "produto/list";
	}

	@GetMapping("new")
	public String form(Model model) {
		model.addAttribute("produto", new Produto());
		carregarCombos(model);
		return "produto/form";
	}

	private void carregarCombos(Model model) {
		model.addAttribute("categorias", categoriaService.findAll());
		model.addAttribute("marcas", marcaService.findAll());
	}

	@PostMapping
	private String save(@Valid Produto produto,
						BindingResult result,
						Model model,
						RedirectAttributes attributes) {
		if (result.hasErrors()) {
			carregarCombos(model);
			return "produto/form";
		}
		produtoService.save(produto);
		attributes.addFlashAttribute("sucesso", "Registro salvo com sucesso!");
		return "redirect:/produto";
	}

	@GetMapping("{id}")
	private String form(@PathVariable("id") Long id, Model model) {
		model.addAttribute("produto", produtoService.findOne(id));
		System.out.println(produtoService.findOne(id));
		carregarCombos(model);
		return "produto/form";
	}

	@DeleteMapping("{id}")
	private String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
		try {
			produtoService.delete(id);
			attributes.addFlashAttribute("sucesso", "Registro removido com sucesso!");
		} catch (Exception ex) {
			attributes.addFlashAttribute("erro", "Falha ao remover o registro!");
		}

		return "redirect:/produto";
	}

	@GetMapping("view/{id}")
	private String view(@PathVariable("id") Long id, Model model) {
		model.addAttribute("produto", produtoService.findOne(id));
		return "produto/view";
	}
}






