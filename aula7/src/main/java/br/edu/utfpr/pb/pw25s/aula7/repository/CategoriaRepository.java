package br.edu.utfpr.pb.pw25s.aula7.repository;

import br.edu.utfpr.pb.pw25s.aula7.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

    @Query("select distinct c from Categoria c "
            + "where c.nome like :search%")
    Page<Categoria> findByNome(String search, Pageable pageable);

}
