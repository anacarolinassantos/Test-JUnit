package br.com.software.springbootapp.service.impl;

import br.com.software.springbootapp.domain.ProdutoEntity;
import br.com.software.springbootapp.model.ProdutoModel;
import br.com.software.springbootapp.repository.ProdutoRepository;
import br.com.software.springbootapp.service.ProdutoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ProdutoServiceImpl extends BaseServiceImpl<ProdutoEntity, Long, ProdutoRepository> implements ProdutoService {

    @Override
    public List<ProdutoModel> buscaTodos() {
        return getRepository().findAll().stream().map(ProdutoModel::new).collect(Collectors.toList());
    }

    @Override
    public List<ProdutoEntity> buscaProdutosComEstoque() {
        return getRepository().findAllByEstoqueGreaterThan(0);
    }

    @Override
    public List<ProdutoEntity> buscaProdutosPorNome(final String termoBuscaNome) {
        return getRepository().findAllByNomeLike("%".concat(termoBuscaNome.toUpperCase()).concat("%"));
    }

}
