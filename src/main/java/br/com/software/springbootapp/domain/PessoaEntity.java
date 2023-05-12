package br.com.software.springbootapp.domain;

import br.com.software.springbootapp.enums.EnumSexo;
import br.com.software.springbootapp.enums.EnumTipoPessoa;
import br.com.software.springbootapp.model.PessoalModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id"})
@ToString
@Entity
@Table(name="pessoa")
public class PessoaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", length = 150)
    private String nome;

    @NotNull
    @Column(name = "cpf", length = 14)
    private String cpf;

    @NotNull
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @NotNull
    @Type(type = "enum")
    @Column(name = "sexo", length = 1)
    private EnumSexo sexo;

    @NotNull
    @Type(type = "enum")
    @Column(name = "tipo", length = 1)
    private EnumTipoPessoa tipoPessoa;

    @NotNull
    @Column(name = "email", length = 200)
    private String email;

    public PessoaEntity(PessoalModel model) {
        super();
        this.nome = model.getNome();
        this.cpf = model.getCpf();
        this.dataNascimento = model.getDataNascimento();
        this.sexo = model.getSexo();
        this.tipoPessoa = model.getTipoPessoa();
        this.email = model.getEmail();
    }

    public void atualiza(PessoalModel model) {
        this.nome = model.getNome();
        this.cpf = model.getCpf();
        this.dataNascimento = model.getDataNascimento();
        this.sexo = model.getSexo();
        this.tipoPessoa = model.getTipoPessoa();
        this.email = model.getEmail();
    }
}
