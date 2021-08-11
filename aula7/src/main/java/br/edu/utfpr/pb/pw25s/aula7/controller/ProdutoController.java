package br.edu.utfpr.pb.pw25s.aula7.controller;

import br.edu.utfpr.pb.pw25s.aula7.model.Produto;
import br.edu.utfpr.pb.pw25s.aula7.service.CategoriaService;
import br.edu.utfpr.pb.pw25s.aula7.service.MarcaService;
import br.edu.utfpr.pb.pw25s.aula7.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


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
	public String list(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
					   Model model) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		Page<Produto> list = this.produtoService.findAll(PageRequest.of(currentPage - 1, pageSize));
		model.addAttribute("list", list);

		if (list.getTotalPages() > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, list.getTotalPages()).boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "produto/list";
	}
	
	@GetMapping(value = {"new", "novo", "form"})
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
	public String save(@Valid Produto produto,
					   BindingResult result,
					   Model model,
					   RedirectAttributes attributes) {
		if ( result.hasErrors() ) {
			model.addAttribute("produto", produto);
			return "produto/form";
		}
		
		produtoService.save(produto);
		attributes.addFlashAttribute("sucesso", 
									 "Registro salvo com sucesso!");
		return "redirect:/produto";
	}

	@GetMapping("{id}") // /produto/25
 	public String form(@PathVariable Long id, Model model) {
		model.addAttribute("produto", produtoService.findOne(id));
		carregarCombos(model);
		return "produto/form";
	}
	
	@DeleteMapping("{id}") // /produto/25
	public String delete(@PathVariable Long id,
						 RedirectAttributes attributes) {
		try {
			produtoService.delete(id);
			attributes.addFlashAttribute("sucesso", 
					"Registro removido com sucesso!");
		} catch (Exception e) {
			attributes.addFlashAttribute("erro", 
					"Falha ao remover o registro!");
		}
		return "redirect:/produto";
	}
}






