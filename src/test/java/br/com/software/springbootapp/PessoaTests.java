package br.com.software.springbootapp;

import br.com.software.springbootapp.config.ConfiguracaoPersonlizada;
import br.com.software.springbootapp.domain.PessoaEntity;
import br.com.software.springbootapp.dto.PessoaDto;
import br.com.software.springbootapp.enums.EnumSexo;
import br.com.software.springbootapp.enums.EnumTipoPessoa;
import br.com.software.springbootapp.exceptions.RegistroNaoEncontradoException;
import br.com.software.springbootapp.helpper.Utils;
import br.com.software.springbootapp.model.PessoalModel;
import br.com.software.springbootapp.service.PessoaService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class PessoaTests {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private ConfiguracaoPersonlizada config;
    
    @Value("classpath:data/PessoaData.json")
    private Resource resourceFile;

    private static final String CPF_TEST = "999.999.999-99";
	
    @BeforeAll
    private void carregaListaPessoasTest() {

        try {
            String json = Utils.readFileAsString(new FileReader(resourceFile.getFile()));
            PessoaDto[] pessoas = new Gson().fromJson(json, PessoaDto[].class);
            var list = Arrays.asList(pessoas).stream().map(dto -> new PessoaEntity(new PessoalModel(dto))).collect(Collectors.toList());
            pessoaService.saveAll(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    @Test
    @DisplayName("Testa quantidade de registros inseridos na carga")
    @Order(1)
    public void quantidadeRegistrosInseridosTest() {
        var list = pessoaService.findAll();
        list.forEach(p -> log.info(p.toString()));
        assertTrue(list.size() == 100);
    }

    @Test
    @DisplayName("Testa inclusão de 1 pessoa")
    @Order(2)
    public void incluiUmaPessoaTest() {
        PessoalModel model = new PessoalModel(null, "FERNANDO", CPF_TEST, LocalDate.of(1983, 9, 1), EnumSexo.MASCULINO, EnumTipoPessoa.FISICA, "fernando.medeiros@edu.sc.senai.br");
        pessoaService.save(new PessoaEntity(model));
        assertTrue(pessoaService.findAll().size() == 101);
    }

    @Test
    @DisplayName("Testa alteração de 1 pessoa")
    @Order(3)
    public void alteraUmaPessoaTest() {

        var lista = pessoaService.findAll();
        var pessoa = lista.stream().filter(p -> p.getCpf().equals(CPF_TEST)).findFirst();

        PessoalModel modelAlteracao = new PessoalModel(null, "FERNANDO ALTERADO", CPF_TEST, LocalDate.of(1983, 9, 1), EnumSexo.MASCULINO, EnumTipoPessoa.FISICA, "fernando.medeiros2@edu.sc.senai.br");
        pessoa.get().atualiza(modelAlteracao);
        pessoaService.save(pessoa.get());

        var pessoaAlterada = lista.stream().filter(p -> p.getCpf().equals(CPF_TEST)).findFirst();

        assertTrue(pessoaAlterada.get().getNome().equals("FERNANDO ALTERADO") && pessoaAlterada.get().getEmail().equals("fernando.medeiros2@edu.sc.senai.br"));
    }

    @Test
    @DisplayName("Testa exclusão de 1 pessoa")
    @Order(4)
    public void excluiUmaPessoaTest() {

        var lista = pessoaService.findAll();
        var pessoa = lista.stream().filter(p -> p.getCpf().equals(CPF_TEST)).findFirst();
        Long idExcluido = pessoa.get().getId();

        pessoaService.delete(pessoa.get());

        assertThrows(RegistroNaoEncontradoException.class, () -> {
            pessoaService.findByIdNotNull(idExcluido);
        });
    }

    @Test
    @DisplayName("Testa exclusão de todas as pessoas")
    @Order(5)
    public void excluiTodasAsPessoasTest() {
        pessoaService.deleteAll(pessoaService.findAll());
        assertEquals(pessoaService.findAll().size(), 0);
    }

}
