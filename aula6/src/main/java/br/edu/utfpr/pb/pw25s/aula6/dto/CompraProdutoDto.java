package br.edu.utfpr.pb.pw25s.aula6.dto;

import lombok.Data;

@Data
public class CompraProdutoDto {

    private Long produtoId;

    private Integer quantidade;

    private Double valor;
}
