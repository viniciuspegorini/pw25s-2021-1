package br.edu.utfpr.pb.pw25s.aula7.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompraDto {

    private String notaFiscal;

    private Long fornecedorId;

    private String observacoes;

    private List<CompraProdutoDto> compraProdutos;

}
