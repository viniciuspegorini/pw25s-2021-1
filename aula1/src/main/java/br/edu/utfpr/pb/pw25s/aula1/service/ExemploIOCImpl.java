package br.edu.utfpr.pb.pw25s.aula1.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("exemplo1")
public class ExemploIOCImpl implements IExemploIOC {

	@Value("${exemploIOC.mensagem}")
	private String mensagem;
	
	@Override
	public String getMensagem() {
		return mensagem;
	}

}
