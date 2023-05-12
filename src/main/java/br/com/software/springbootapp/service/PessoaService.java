package br.com.software.springbootapp.service;

import br.com.software.springbootapp.domain.PessoaEntity;
import br.com.software.springbootapp.enums.EnumSexo;
import br.com.software.springbootapp.model.PessoalModel;

import java.util.List;

public interface PessoaService extends BaseService<PessoaEntity, Long>  {

    List<PessoalModel> cadastraPessoas();
    List<PessoalModel> buscaTodos();
    List<PessoalModel> buscaPorSexo(EnumSexo sexo);
    List<PessoaEntity> buscaPessoasPorNome(final String termoBuscaNome);

}
