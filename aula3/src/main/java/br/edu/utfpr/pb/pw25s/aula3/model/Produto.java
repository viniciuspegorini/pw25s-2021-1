package br.edu.utfpr.pb.pw25s.aula3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Produto implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "O campo nome deve ser preenchido.")
	@Column(length = 100, nullable = false)
	private String nome;
	
	@Column(length = 1024, nullable = false)
	private String descricao;

	@NotNull(message = "O campo valor não pode ser nulo.")
	@Column(nullable = false)
	private Double valor; 
	
	@ManyToOne
	@JoinColumn(name = "categoria_id", referencedColumnName = "id")
	private Categoria categoria;

	@ManyToOne
	@JoinColumn(name = "marca_id", referencedColumnName = "id")
	private Marca marca;

}
