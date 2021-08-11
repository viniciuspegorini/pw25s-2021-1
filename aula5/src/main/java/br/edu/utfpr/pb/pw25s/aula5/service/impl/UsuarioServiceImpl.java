package br.edu.utfpr.pb.pw25s.aula5.service.impl;

import br.edu.utfpr.pb.pw25s.aula5.model.Usuario;
import br.edu.utfpr.pb.pw25s.aula5.repository.UsuarioRepository;
import br.edu.utfpr.pb.pw25s.aula5.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl extends CrudServiceImpl<Usuario, Long>
		implements UsuarioService, UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	protected JpaRepository<Usuario, Long> getRepository() {
		return usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.usuarioRepository.findByUsername(username).orElseThrow( ()
				-> new UsernameNotFoundException("Usuário não encontrado")
		);
	}

}
