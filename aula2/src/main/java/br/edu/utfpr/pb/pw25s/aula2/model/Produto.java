package br.edu.utfpr.pb.pw25s.aula2.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "produto")
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 512, nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Double valor;

    @Column(name = "data_fabricacao", nullable = false)
    private LocalDate dataFabricacao;

    @ManyToOne
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    private Categoria categoria;

}
