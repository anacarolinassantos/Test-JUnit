package br.com.software.springbootapp.service.impl;

import br.com.software.springbootapp.domain.PessoaEntity;
import br.com.software.springbootapp.dto.PessoaDto;
import br.com.software.springbootapp.enums.EnumSexo;
import br.com.software.springbootapp.helpper.Utils;
import br.com.software.springbootapp.model.PessoalModel;
import br.com.software.springbootapp.repository.PessoaRepository;
import br.com.software.springbootapp.service.PessoaService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class PessoaServiceImpl extends BaseServiceImpl<PessoaEntity, Long, PessoaRepository> implements PessoaService {

    @Value("classpath:data/PessoaData.json")
    Resource resourceFile;

    @Override
    public List<PessoalModel> cadastraPessoas() {

        if (getRepository().findAll().isEmpty()) {
            try {
                String json = Utils.readFileAsString(new FileReader(resourceFile.getFile()));
                PessoaDto[] pessoas = new Gson().fromJson(json, PessoaDto[].class);
                var list = Arrays.asList(pessoas).stream().map(dto -> new PessoaEntity(new PessoalModel(dto))).collect(Collectors.toList());
                getRepository().saveAll(list);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return getRepository().findAll().stream().map(PessoalModel::new).collect(Collectors.toList());
    }

    @Override
    public List<PessoalModel> buscaTodos() {
        return getRepository().findAll().stream().map(PessoalModel::new).collect(Collectors.toList());
    }

    @Override
    public List<PessoalModel> buscaPorSexo(EnumSexo sexo) {
        return getRepository().findBySexo(sexo).stream().map(PessoalModel::new).collect(Collectors.toList());
    }

    @Override
    public List<PessoaEntity> buscaPessoasPorNome(final String termoBuscaNome) {
        return getRepository().findAllByNomeLike("%".concat(termoBuscaNome.toUpperCase()).concat("%"));
    }
}
