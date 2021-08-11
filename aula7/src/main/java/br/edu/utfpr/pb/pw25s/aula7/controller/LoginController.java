package br.edu.utfpr.pb.pw25s.aula7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("login")
	public String login() {
		return "login";
	}
}
