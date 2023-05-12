package br.com.software.springbootapp;

import br.com.software.springbootapp.config.ConfiguracaoPersonlizada;
import br.com.software.springbootapp.domain.PessoaEntity;
import br.com.software.springbootapp.domain.ProdutoEntity;
import br.com.software.springbootapp.dto.PessoaDto;
import br.com.software.springbootapp.dto.ProdutoDto;
import br.com.software.springbootapp.enums.EnumSexo;
import br.com.software.springbootapp.enums.EnumTipoPessoa;
import br.com.software.springbootapp.helpper.Utils;
import br.com.software.springbootapp.model.PessoalModel;
import br.com.software.springbootapp.model.ProdutoModel;
import br.com.software.springbootapp.service.PessoaService;
import br.com.software.springbootapp.service.ProdutoService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;

import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdutoTests {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ConfiguracaoPersonlizada config;

    @Value("classpath:data/ProdutoData.json")
    private Resource resourceFile;

    private static final BigDecimal CODIGO_BARRA = new BigDecimal("7896541236547");

    @BeforeAll
    private void carregaListaProdutoTest() {
        try {
            String json = Utils.readFileAsString(new FileReader(resourceFile.getFile()));
            var produtos = new Gson().fromJson(json, ProdutoDto[].class);
            var list = Arrays.stream(produtos).map(dto -> new ProdutoEntity(new ProdutoModel(dto)))
                    .collect(Collectors.toList());
            produtoService.saveAll(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("Testa quantidade de registros inseridos na carga")
    @Order(1)
    public void quantidadeRegistrosInseridosTest() {
        var list = produtoService.findAll();
        list.forEach(p -> log.info(p.toString()));
        assertTrue(list.size() == 2);
    }

    @Test
    @DisplayName("Testa inclusão de 1 produto")
    @Order(2)
    public void incluiUmProdutoTest() {
        var model = new ProdutoModel(null,"Rexona", "Desodorante antitranspirante", CODIGO_BARRA, 30);
        produtoService.save(new ProdutoEntity(model));
        assertTrue(produtoService.findAll().size() == 3);
    }

    @Test
    @DisplayName("Testa alteração de 1 produto")
    @Order(3)
    public void alteraUmProdutoTest() {

        var lista = produtoService.findAll();
        var produto = lista.stream().filter(p -> p.getCodigoBarra().equals(CODIGO_BARRA)).findFirst();

        ProdutoModel modelAlteracao = new ProdutoModel(null,"Rexona Dove", "Desodorante Dove antitranspirante", CODIGO_BARRA, 25);
        produto.get().atualiza(modelAlteracao);
        produtoService.save(produto.get());

        var produtoAlterado = lista.stream().filter(p -> p.getCodigoBarra().equals(CODIGO_BARRA)).findFirst();

        assertTrue(produtoAlterado.get().getNome().equals("Rexona Dove") && produtoAlterado.get().getEstoque().equals(25));
    }

}
