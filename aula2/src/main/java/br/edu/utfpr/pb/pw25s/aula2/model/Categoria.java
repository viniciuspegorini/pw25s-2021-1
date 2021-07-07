package br.edu.utfpr.pb.pw25s.aula2.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categoria")
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

}
