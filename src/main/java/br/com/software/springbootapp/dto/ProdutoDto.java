package br.com.software.springbootapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ProdutoDto {

    private String nome;
    private String descricao;
    private BigDecimal codigoBarra;
    private Integer estoque;

}
