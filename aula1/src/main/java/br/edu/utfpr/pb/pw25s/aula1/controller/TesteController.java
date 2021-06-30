package br.edu.utfpr.pb.pw25s.aula1.controller;

import br.edu.utfpr.pb.pw25s.aula1.service.ExemploIOCImpl;
import br.edu.utfpr.pb.pw25s.aula1.service.IExemploIOC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("teste")
public class TesteController {
	
	@Autowired
	@Qualifier("exemplo1")
	IExemploIOC exemplo1;
	
	@Autowired
	@Qualifier("exemplo2")
	IExemploIOC exemplo2;
	
	@GetMapping(value = {"", "/"})
	@ResponseBody
	public String teste() {
		return exemplo1.getMensagem();
	}

	@GetMapping("exemplo2")
	@ResponseBody
	public String teste2() {
		return exemplo2.getMensagem();
	}
}
