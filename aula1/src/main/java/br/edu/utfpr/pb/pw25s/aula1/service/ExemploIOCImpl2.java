package br.edu.utfpr.pb.pw25s.aula1.service;

import org.springframework.stereotype.Service;

@Service("exemplo2")
public class ExemploIOCImpl2 implements IExemploIOC {

	@Override
	public String getMensagem() {
		return "Mensagem do exemplo 2 !!";
	}

}
