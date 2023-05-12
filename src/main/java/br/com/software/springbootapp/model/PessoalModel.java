package br.com.software.springbootapp.model;

import br.com.software.springbootapp.domain.PessoaEntity;
import br.com.software.springbootapp.dto.PessoaDto;
import br.com.software.springbootapp.enums.EnumSexo;
import br.com.software.springbootapp.enums.EnumTipoPessoa;
import br.com.software.springbootapp.helpper.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class PessoalModel {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String cpf;

    @NotNull
    private LocalDate dataNascimento;

    @NotNull
    private EnumSexo sexo;

    @NotNull
    private EnumTipoPessoa tipoPessoa;

    @NotNull
    private String email;

    public PessoalModel(PessoaDto dto) {
        this.nome = dto.getNome();
        this.cpf = dto.getCpf();
        this.dataNascimento = LocalDate.parse(dto.getData_nasc(), DateUtil.dtfDataBrasil);
        this.sexo = EnumSexo.getByNome(dto.getSexo());
        this.tipoPessoa = EnumTipoPessoa.FISICA;
        this.email = dto.getEmail();
    }

    public PessoalModel(PessoaEntity entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.cpf = entity.getCpf();
        this.dataNascimento = entity.getDataNascimento();
        this.sexo = entity.getSexo();
        this.tipoPessoa = entity.getTipoPessoa();
        this.email = entity.getEmail();
    }
}
