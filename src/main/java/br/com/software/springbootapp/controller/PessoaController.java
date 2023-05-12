package br.com.software.springbootapp.controller;

import br.com.software.springbootapp.domain.PessoaEntity;
import br.com.software.springbootapp.enums.EnumSexo;
import br.com.software.springbootapp.exceptions.RegistroNaoEncontradoException;
import br.com.software.springbootapp.model.MessageModel;
import br.com.software.springbootapp.model.PessoalModel;
import br.com.software.springbootapp.service.PessoaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pessoa")
@Slf4j
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/lista-todos")
    public List<PessoalModel> listaTodos() {
        return pessoaService.findAll().stream().map(PessoalModel::new).collect(Collectors.toList());
    }

    @GetMapping("/lista-todos-por-nome/{termoBusca}")
    public List<PessoalModel> listaTodosPorNome(@PathVariable String termoBusca) {
        return pessoaService.buscaPessoasPorNome(termoBusca).stream().map(PessoalModel::new).collect(Collectors.toList());
    }

    @GetMapping("/lista-por-sexo/{sexo}")
    public List<PessoalModel> listaPorSexoTodos(@PathVariable String sexo) {
        return pessoaService.buscaPorSexo(EnumSexo.getBySigla(sexo));
    }

    @PostMapping("/cadastra-pessoas")
    public List<PessoalModel> cadastraPessoas() {
        return pessoaService.cadastraPessoas();
    }

    @PostMapping("/criar")
    public PessoalModel criarPessoa(@RequestBody PessoalModel model) {
        return new PessoalModel(pessoaService.save(new PessoaEntity(model)));
    }

    @PutMapping("/alterar/{id}")
    public PessoalModel alterarPessoa(@PathVariable Long id, @RequestBody PessoalModel model) {

        var pessoa = pessoaService.findById(id);

        pessoa.ifPresentOrElse(p -> p.atualiza(model), () -> {
            throw new RegistroNaoEncontradoException("Registro não encontrado");
        });

        return new PessoalModel(pessoaService.save(pessoa.get()));
    }

    @DeleteMapping("/remover/{id}")
    public MessageModel removerPessoa(@PathVariable Long id) {

        var pessoa = pessoaService.findById(id);

        pessoa.ifPresentOrElse(p -> pessoaService.delete(p), () -> {
            throw new RegistroNaoEncontradoException("Registro não encontrado");
        });

        return new MessageModel("Removido com sucesso!");
    }
}
