package br.com.software.springbootapp.repository;

import br.com.software.springbootapp.domain.PessoaEntity;
import br.com.software.springbootapp.domain.ProdutoEntity;
import br.com.software.springbootapp.enums.EnumSexo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends BaseRepository<PessoaEntity, Long> {

    List<PessoaEntity> findBySexo(EnumSexo sexo);
    List<PessoaEntity> findAllByNomeLike(String termoBuscaNome);

}
