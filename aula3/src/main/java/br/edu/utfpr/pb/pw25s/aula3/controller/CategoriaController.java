package br.edu.utfpr.pb.pw25s.aula3.controller;

import br.edu.utfpr.pb.pw25s.aula3.model.Categoria;
import br.edu.utfpr.pb.pw25s.aula3.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    private String list(Model model) {
        model.addAttribute("categorias", categoriaService.findAll());
        return "categoria/list";
    }

    @GetMapping(value = {"new", "novo", "form"})
    private String form(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categoria/form";
    }

    @PostMapping
    private String save(Categoria categoria) {

        categoriaService.save(categoria);
        return "redirect:/categoria";
    }


}
