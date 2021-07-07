package br.edu.utfpr.pb.pw25s.aula2.repository;

import br.edu.utfpr.pb.pw25s.aula2.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // SELECT * FROM Categoria Where nome LIKE ':nome'
    List<Categoria> findByNomeLike(String nome);

    // SELECT * FROM Categoria Where nome LIKE '%:nome%'
    List<Categoria> findByNomeContains(String nome);

    // SELECT * FROM Categoria Where nome LIKE ':nome%'
    List<Categoria> findByNomeStartingWith(String nome);

    // SELECT * FROM Categoria Where nome LIKE '%:nome'
    List<Categoria> findByNomeEndingWith(String nome);
}
