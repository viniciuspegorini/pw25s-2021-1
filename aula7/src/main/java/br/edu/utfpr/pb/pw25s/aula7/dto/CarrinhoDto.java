package br.edu.utfpr.pb.pw25s.aula7.dto;

import lombok.Data;

import java.util.List;

@Data
public class CarrinhoDto {

    private String tipoPagamento;
    private List<CompraProdutoDto> items;

}
