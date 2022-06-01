package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.model.EscalaGrupoComunhao;
import com.olimpia.tcc.nossaigrejaapp.repository.UserRepository;
import com.olimpia.tcc.nossaigrejaapp.testhelpers.EscalaGrupoComunhaoTestHelper;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@ActiveProfiles("test")
@SpringBootTest
public class EscalaGrupoComunhaoServiceIntegracaoTest {

    @Autowired
    private EscalaGrupoComunhaoService service;

    @Autowired
    private GrupoComunhaoService grupoComunhaoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private EscalaGrupoComunhao escalaGrupoComunhao;

    @BeforeEach
    public void setup(){
        escalaGrupoComunhao = EscalaGrupoComunhaoTestHelper.createEscala(grupoComunhaoService,userRepository,modelMapper);
    }

    @AfterEach
    public void tearDown(){
        service.deleteAll();
        grupoComunhaoService.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void naoDeveCriarEscalaComMesmaDataParaMesmoGrupo(){
        service.criarOuAlterar(escalaGrupoComunhao);

        EscalaGrupoComunhao escalaNova = SerializationUtils.clone(escalaGrupoComunhao);
        escalaNova.setId(null);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> service.criarOuAlterar(escalaNova));
    }

    @Test
    void deveEncontrarProximasEscalasPorGrupo(){
        service.criarOuAlterar(escalaGrupoComunhao);

        int quantidadeEscalas = service
                .encontrarProximasEscalasPorGrupoParticipante(escalaGrupoComunhao.getGrupoComunhao().getId()).size();
        Assertions.assertEquals(1,quantidadeEscalas);
    }

    @Test
    void naoDeveRetornarEscalaAnteriorHoje(){
        escalaGrupoComunhao.setData(LocalDate.now().minusDays(1));
        service.criarOuAlterar(escalaGrupoComunhao);

        int quantidadeEscalas = service
                .encontrarProximasEscalasPorGrupoParticipante(escalaGrupoComunhao.getGrupoComunhao().getId()).size();
        Assertions.assertEquals(0,quantidadeEscalas);
    }
}
