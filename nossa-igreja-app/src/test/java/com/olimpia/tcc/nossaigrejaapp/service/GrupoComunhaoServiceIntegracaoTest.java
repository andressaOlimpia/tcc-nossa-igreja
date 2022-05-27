package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.dto.GrupoComunhaoDTO;
import com.olimpia.tcc.nossaigrejaapp.model.DiaSemanaEnum;
import com.olimpia.tcc.nossaigrejaapp.model.GrupoComunhao;
import com.olimpia.tcc.nossaigrejaapp.model.User;
import com.olimpia.tcc.nossaigrejaapp.repository.EnderecoRepository;
import com.olimpia.tcc.nossaigrejaapp.repository.GrupoComunhaoRepository;
import com.olimpia.tcc.nossaigrejaapp.repository.UserRepository;
import com.olimpia.tcc.nossaigrejaapp.testhelpers.GrupoComunhaoTestHelper;
import com.olimpia.tcc.nossaigrejaapp.testhelpers.UsuarioTestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import java.math.BigInteger;

@ActiveProfiles("test")
@SpringBootTest
class GrupoComunhaoServiceIntegracaoTest {

    @Autowired
    private GrupoComunhaoService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private GrupoComunhaoRepository repository;

    @Autowired
    private ModelMapper modelMapper;


    private GrupoComunhaoDTO grupoComunhaoDTO;

    @BeforeEach
    public void setup(){
        grupoComunhaoDTO = GrupoComunhaoTestHelper.createDTO(userRepository, modelMapper);
    }

    @AfterEach
    public void tearDown(){
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void deveCadastrarGrupoComunhao() {
       GrupoComunhao grupoComunhao = modelMapper.map(grupoComunhaoDTO, GrupoComunhao.class);
       GrupoComunhao grupoSalvo = service.save(grupoComunhao);

        Assertions.assertEquals(grupoComunhao.getDiaSemana(), grupoSalvo.getDiaSemana());
        Assertions.assertEquals(grupoComunhao.getHorario(), grupoSalvo.getHorario());
        Assertions.assertEquals(grupoComunhao.getNome(), grupoSalvo.getNome());
        Assertions.assertEquals(grupoComunhao.getLider(), grupoSalvo.getLider());
        Assertions.assertEquals(grupoComunhao.getParticipantes(), grupoSalvo.getParticipantes());
        Assertions.assertEquals(grupoComunhao.getMaxParticipantes(), grupoSalvo.getMaxParticipantes());
        Assertions.assertEquals(grupoComunhao.getEndereco().getCep(), grupoSalvo.getEndereco().getCep());
    }

    @Test
    @Transactional
    void deveCadastrarEnderecoQuandoCadastrarGrupoComunhao() {
        GrupoComunhao grupoComunhao = modelMapper.map(grupoComunhaoDTO, GrupoComunhao.class);
        Assertions.assertNull(grupoComunhao.getEndereco().getId());

        GrupoComunhao grupoSalvo = service.save(grupoComunhao);
        Assertions.assertNotNull(grupoSalvo.getEndereco().getId());
    }

    @Test
    @Transactional
    void deveAlterarGrupoComunhao() throws Exception {
        GrupoComunhao grupoComunhao = modelMapper.map(grupoComunhaoDTO, GrupoComunhao.class);
        GrupoComunhao grupoAntigo = service.save(grupoComunhao);

        GrupoComunhao grupoComAlteracoes = modelMapper.map(grupoComunhaoDTO, GrupoComunhao.class);
        grupoComAlteracoes.setId(grupoAntigo.getId());
        grupoComAlteracoes.setNome("Nome alterado");
        grupoComAlteracoes.setHorario(grupoComunhao.getHorario().minusHours(2));
        grupoComAlteracoes.setDiaSemana(DiaSemanaEnum.SEG);
        grupoComAlteracoes.setParticipantes(null);
        grupoComAlteracoes.setLider(null);
        grupoComAlteracoes.getEndereco().setCep("000000000");
        grupoComAlteracoes.setMaxParticipantes(grupoComunhao.getMaxParticipantes().add(BigInteger.ONE));

        GrupoComunhao grupoAlterado = service.edit(grupoComAlteracoes);

        Assertions.assertEquals(grupoAlterado.getId(), grupoAntigo.getId());

        Assertions.assertEquals(grupoAlterado.getNome(), grupoComAlteracoes.getNome());
        Assertions.assertEquals(grupoAlterado.getHorario(), grupoComAlteracoes.getHorario());
        Assertions.assertEquals(grupoAlterado.getDiaSemana(), grupoComAlteracoes.getDiaSemana());
        Assertions.assertEquals(grupoAlterado.getParticipantes(), grupoComAlteracoes.getParticipantes());
        Assertions.assertEquals(grupoAlterado.getLider(), grupoComAlteracoes.getLider());
        Assertions.assertEquals(grupoAlterado.getMaxParticipantes(), grupoComAlteracoes.getMaxParticipantes());
        Assertions.assertEquals(grupoAlterado.getEndereco().getCep(), grupoComAlteracoes.getEndereco().getCep());

    }

    @Test
    @Transactional
    void naoDeveCadastrarNovoEnderecoQuandoEditarGrupoComunhao() throws Exception {
        GrupoComunhao grupoComunhao = modelMapper.map(grupoComunhaoDTO, GrupoComunhao.class);
        GrupoComunhao grupoAntigo = service.save(grupoComunhao);

        GrupoComunhao grupoComAlteracoes = modelMapper.map(grupoComunhaoDTO, GrupoComunhao.class);
        grupoComAlteracoes.setId(grupoAntigo.getId());
        grupoComAlteracoes.getEndereco().setCep("000000000");

        GrupoComunhao grupoAlterado = service.edit(grupoComAlteracoes);
        Assertions.assertEquals(grupoAlterado.getEndereco().getId(), grupoAntigo.getEndereco().getId());
    }

    @Test
    @Transactional
    void deveConsultarTodosGruposComunhao(){
        Assertions.assertTrue( service.findAll().isEmpty());

        service.save(modelMapper.map(grupoComunhaoDTO, GrupoComunhao.class));
        service.save(modelMapper.map(grupoComunhaoDTO, GrupoComunhao.class));
        service.save(modelMapper.map(grupoComunhaoDTO, GrupoComunhao.class));

        Assertions.assertEquals(3, service.findAll().size());
    }

    @Test
    @Transactional
    void deveConsultarGrupoComunhaoPorId() throws Exception {
        GrupoComunhao grupoComunhao = modelMapper.map(grupoComunhaoDTO, GrupoComunhao.class);
        GrupoComunhao grupoSalvo = service.save(grupoComunhao);

        GrupoComunhao grupoConsultado = service.findById(grupoSalvo.getId());

        Assertions.assertEquals(grupoConsultado,grupoSalvo);
    }

    @Test
    @Transactional
    void deveExcluirGrupoComunhaoPorId() {
        GrupoComunhao grupoComunhao = modelMapper.map(grupoComunhaoDTO, GrupoComunhao.class);
        GrupoComunhao grupoSalvo = service.save(grupoComunhao);

        service.delete(grupoSalvo.getId());
        Assertions.assertEquals(0, service.findAll().size());
    }

    @Test
    @Transactional
    void deveExcluirEnderecoAoExcluirGrupoComunhao() {
        GrupoComunhao grupoComunhao = modelMapper.map(grupoComunhaoDTO, GrupoComunhao.class);
        GrupoComunhao grupoSalvo = service.save(grupoComunhao);

        Assertions.assertEquals(1, enderecoRepository.findAll().size());

        service.delete(grupoSalvo.getId());
        Assertions.assertEquals(0, enderecoRepository.findAll().size());
    }

    @Test
    @Transactional
    void deveIncluirParticipanteEmGrupoComunhao() throws Exception {
        GrupoComunhao grupoComunhao = modelMapper.map(grupoComunhaoDTO, GrupoComunhao.class);
        grupoComunhao.setParticipantes(null);
        GrupoComunhao grupoSalvo = service.save(grupoComunhao);

        User usuarioParticipante = userRepository
                .save(modelMapper.map(UsuarioTestHelper.createUserDTO(),User.class));

        GrupoComunhao grupoComParticipante = service
                .inscreverParticipante(grupoSalvo.getId(), usuarioParticipante.getId());

        Assertions.assertEquals(usuarioParticipante, grupoComParticipante.getParticipantes().get(0));
    }
}
