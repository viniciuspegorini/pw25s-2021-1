package br.edu.utfpr.pb.pw25s.aula3.controller;

import br.edu.utfpr.pb.pw25s.aula3.model.Categoria;
import br.edu.utfpr.pb.pw25s.aula3.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

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
    private String save(@Valid  Categoria categoria,
                        BindingResult result,
                        Model model,
                        RedirectAttributes attributes) {

            if (result.hasErrors()) {
                return "categoria/form";
            }
            categoriaService.save(categoria);

            attributes.addFlashAttribute("sucesso", "Registro salvo com sucesso!");
            return "redirect:/categoria";
    }

    @GetMapping("{id}")
    private String form(@PathVariable("id") Long id, Model model) {
        if ( categoriaService.exists( id ) ) {
            model.addAttribute("categoria", categoriaService.findOne(id));
        } else {
            model.addAttribute("categoria", new Categoria());
        }
        return "categoria/form";
    }


    @DeleteMapping("{id}")
    private String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            categoriaService.delete(id);
            attributes.addFlashAttribute("sucesso", "Registro removido com sucesso!");
        } catch (Exception ex) {
            attributes.addFlashAttribute("erro", "Falha ao remover a categoria!");
        }

        return "redirect:/categoria";
    }


}
