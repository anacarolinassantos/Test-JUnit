package br.com.software.springbootapp.repository;

import br.com.software.springbootapp.domain.ProdutoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends BaseRepository<ProdutoEntity, Long> {

    List<ProdutoEntity> findAllByEstoqueGreaterThan(Integer estoqueMinimo);
    List<ProdutoEntity> findAllByNomeLike(String termoBuscaNome);

}
