package br.edu.utfpr.pb.pw25s.aula6.dto;

import br.edu.utfpr.pb.pw25s.aula6.model.Usuario;

public class UsuarioDto {

    private Long id;
    private String nome;

    public UsuarioDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
    }

    public Usuario getEntity() {
        Usuario usuario = new Usuario();
        usuario.setId(this.id);
        usuario.setNome(this.nome);
        return  usuario;
    }
}
