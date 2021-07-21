package br.edu.utfpr.pb.pw25s.aula4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("")
	public String index() {
		return "index";
	}
	
	@GetMapping(value = {"teste", "teste2"})
	public String teste() {
		return "login";
	}
	
}
