package br.edu.utfpr.pb.pw25s.aula6.dto;

import br.edu.utfpr.pb.pw25s.aula6.model.Compra;
import br.edu.utfpr.pb.pw25s.aula6.model.CompraProduto;
import br.edu.utfpr.pb.pw25s.aula6.model.CompraProdutoPK;
import br.edu.utfpr.pb.pw25s.aula6.model.Produto;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CompraDto {

    private String notaFiscal;

    private Long fornecedorId;

    private String observacoes;

    private List<CompraProdutoDto> compraProdutos;

}
