package br.edu.utfpr.pb.pw25s.aula6.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Compra implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nota_fiscal", nullable = false)
    private String notaFiscal;

    @Column(nullable = false)
    private LocalDate data;

    @Column(name = "observacoes", length = 2048)
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", referencedColumnName = "id")
    private Fornecedor fornecedor;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "id.compra")
    private List<CompraProduto> compraProdutos;

    public double getValorTotal() {
        return compraProdutos.stream()
                .map(cp -> cp.getValor() * cp.getQuantidade())
                .collect(Collectors.summingDouble(Double::doubleValue));
    }
}
