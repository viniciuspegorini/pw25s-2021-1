package br.edu.utfpr.pb.pw25s.aula7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@SpringBootApplication
public class Aula7Application {

	public static void main(String[] args) {
		SpringApplication.run(Aula7Application.class, args);
	}

	@Bean
	public LayoutDialect layoutDialect() {
	    return new LayoutDialect();
	}
}
