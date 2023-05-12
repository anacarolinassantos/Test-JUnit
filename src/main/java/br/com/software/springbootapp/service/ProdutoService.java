package br.com.software.springbootapp.service;

import br.com.software.springbootapp.domain.ProdutoEntity;
import br.com.software.springbootapp.model.ProdutoModel;

import java.util.List;

public interface ProdutoService extends BaseService<ProdutoEntity, Long>  {

    List<ProdutoModel> buscaTodos();
    List<ProdutoEntity> buscaProdutosComEstoque();
    List<ProdutoEntity> buscaProdutosPorNome(final String termoBuscaNome);

}
