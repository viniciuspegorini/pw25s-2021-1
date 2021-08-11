package br.edu.utfpr.pb.pw25s.aula7.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class CompraProduto implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CompraProdutoPK id;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private Double valor;

}
