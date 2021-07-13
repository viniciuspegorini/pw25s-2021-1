package br.edu.utfpr.pb.pw25s.aula3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home")
public class HomeController {

    @GetMapping({"index"})
    public String abrirPaginaInicial() {
        return "index";
    }

    @GetMapping("teste")
    public String abrirIndex() {
        return "index";
    }

    @GetMapping
    public String abrirHome() {
        return "home";
    }
}
