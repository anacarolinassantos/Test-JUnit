package br.com.software.springbootapp.model;

import br.com.software.springbootapp.domain.ProdutoEntity;
import br.com.software.springbootapp.dto.ProdutoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ProdutoModel {

    private Long id;

    @NotNull
    private String nome;

    private String descricao;

    @NotNull
    private BigDecimal codigoBarra;

    @NotNull
    private Integer estoque;

    public ProdutoModel(ProdutoEntity entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.descricao = entity.getDescricao();
        this.codigoBarra = entity.getCodigoBarra();
        this.estoque = entity.getEstoque();
    }

    public ProdutoModel(ProdutoDto dto) {
        this.nome = dto.getNome();
        this.descricao = dto.getDescricao();
        this.codigoBarra = dto.getCodigoBarra();
        this.estoque = dto.getEstoque();
    }
}
