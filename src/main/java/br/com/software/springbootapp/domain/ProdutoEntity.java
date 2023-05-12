package br.com.software.springbootapp.domain;

import br.com.software.springbootapp.model.ProdutoModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id"})
@ToString
@Entity
@Table(name="produto")
public class ProdutoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", length = 150)
    private String nome;

    @Column(name = "descricao", length = 512)
    private String descricao;

    @NotNull
    @Column(name = "codigo_barra", precision = 20, scale = 0)
    private BigDecimal codigoBarra;

    @NotNull
    @Column(name = "estoque")
    private Integer estoque;

    public ProdutoEntity(ProdutoModel model) {
        super();
        this.nome = model.getNome();
        this.descricao = model.getDescricao();
        this.codigoBarra = model.getCodigoBarra();
        this.estoque = model.getEstoque();
    }

    public void atualiza(ProdutoModel model) {
        this.nome = model.getNome();
        this.descricao = model.getDescricao();
        this.codigoBarra = model.getCodigoBarra();
        this.estoque = model.getEstoque();
    }
}
