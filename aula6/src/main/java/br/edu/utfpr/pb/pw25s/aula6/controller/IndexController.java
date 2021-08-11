package br.edu.utfpr.pb.pw25s.aula6.controller;

import br.edu.utfpr.pb.pw25s.aula6.model.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class IndexController {

	@GetMapping("")
	public String index(Principal principal) {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		System.out.println("--------- Principal Spring: " + obj);
		System.out.println("--------- Principal: " + principal.toString());
		if (obj instanceof Usuario) {
			Usuario usuario = (Usuario) obj;
			System.out.println("--------- Usuário: " + usuario);
		}

		return "index";
	}

}
