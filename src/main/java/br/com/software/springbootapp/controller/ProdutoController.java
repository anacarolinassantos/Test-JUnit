package br.com.software.springbootapp.controller;

import br.com.software.springbootapp.domain.ProdutoEntity;
import br.com.software.springbootapp.exceptions.RegistroNaoEncontradoException;
import br.com.software.springbootapp.model.MessageModel;
import br.com.software.springbootapp.model.ProdutoModel;
import br.com.software.springbootapp.service.ProdutoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produto")
@Slf4j
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/lista-todos")
    public List<ProdutoModel> listaTodos() {
        return produtoService.buscaTodos();
    }

    @GetMapping("/lista-todos-por-nome/{termoBusca}")
    public List<ProdutoModel> listaTodosPorNome(@PathVariable String termoBusca) {
        return produtoService.buscaProdutosPorNome(termoBusca).stream().map(ProdutoModel::new).collect(Collectors.toList());
    }

    @PostMapping("/criar")
    public ProdutoModel criarProduto(@RequestBody ProdutoModel model) {
        return new ProdutoModel(produtoService.save(new ProdutoEntity(model)));
    }

    @PutMapping("/alterar/{id}")
    public ProdutoModel alterarProduto(@PathVariable Long id, @RequestBody ProdutoModel model) {

        var pessoa = produtoService.findById(id);

        pessoa.ifPresentOrElse(p -> p.atualiza(model), () -> {
            throw new RegistroNaoEncontradoException("Registro não encontrado");
        });

        return new ProdutoModel(produtoService.save(pessoa.get()));
    }

    @DeleteMapping("/remover/{id}")
    public MessageModel removerProduto(@PathVariable Long id) {

        var pessoa = produtoService.findById(id);

        pessoa.ifPresentOrElse(p -> produtoService.delete(p), () -> {
            throw new RegistroNaoEncontradoException("Registro não encontrado");
        });

        return new MessageModel("Removido com sucesso!");
    }
}
