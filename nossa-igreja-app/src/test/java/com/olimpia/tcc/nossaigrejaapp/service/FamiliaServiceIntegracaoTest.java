package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.dto.FamiliaDTO;
import com.olimpia.tcc.nossaigrejaapp.model.Familia;
import com.olimpia.tcc.nossaigrejaapp.repository.EnderecoRepository;
import com.olimpia.tcc.nossaigrejaapp.repository.FamiliaRepository;
import com.olimpia.tcc.nossaigrejaapp.repository.PessoaSemCadastroRepository;
import com.olimpia.tcc.nossaigrejaapp.repository.UserRepository;
import com.olimpia.tcc.nossaigrejaapp.testhelpers.FamiliaTestHelper;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

@ActiveProfiles("test")
@SpringBootTest
public class FamiliaServiceIntegracaoTest {

    @Autowired
    private FamiliaService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaSemCadastroRepository pessoaSemCadastroRepository;

    @Autowired
    private FamiliaRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    private FamiliaDTO familiaDTO;

    @BeforeEach
    public void setup(){
        familiaDTO = FamiliaTestHelper.createFamiliaDTO(userRepository, modelMapper);
    }

    @AfterEach
    public void tearDown(){
        repository.deleteAll();
        userRepository.deleteAll();
        pessoaSemCadastroRepository.deleteAll();
    }

    @Test
    @Transactional
    void deveCadastrarFamilia() {
        Familia familia = modelMapper.map(familiaDTO, Familia.class);
        Familia familiaSalva = service.save(familia);

        Assertions.assertEquals(familia.getPrincipal(), familiaSalva.getPrincipal());
        Assertions.assertEquals(familia.getFamiliares(), familiaSalva.getFamiliares());
        Assertions.assertEquals(familia.getFamiliaresSemCadastro(), familiaSalva.getFamiliaresSemCadastro());
        Assertions.assertEquals(familia.getEndereco().getCep(), familiaSalva.getEndereco().getCep());
    }

    @Test
    @Transactional
    void deveCadastrarEnderecoQuandoCadastrarFamilia() {
        Familia familia = modelMapper.map(familiaDTO, Familia.class);
        Assertions.assertNull(familia.getEndereco().getId());

        Familia familiaSalva = service.save(familia);
        Assertions.assertNotNull(familiaSalva.getEndereco().getId());
    }

    @Test
    @Transactional
    void deveSalvarPessoaSemCadastroQuandoCadastrarFamilia() {
        Familia familia = modelMapper.map(familiaDTO, Familia.class);
        Assertions.assertNull(familia.getFamiliaresSemCadastro().get(0).getId());

        Familia familiaSalva = service.save(familia);
        Assertions.assertNotNull(familiaSalva.getFamiliaresSemCadastro().get(0).getId());
    }

    @Test
    @Transactional
    void deveAlterarFamilia() throws Exception {
        Familia familia = service.save(modelMapper.map(familiaDTO, Familia.class));

        Familia familiaComAlteracoes = SerializationUtils.clone(familia);
        familiaComAlteracoes.setFamiliares(null);
        familiaComAlteracoes.setFamiliaresSemCadastro(null);
        familiaComAlteracoes.getEndereco().setCep("000000000");

        service.edit(familiaComAlteracoes);

        Assertions.assertEquals(familia.getFamiliares(), familiaComAlteracoes.getFamiliares());
        Assertions.assertEquals(familia.getFamiliaresSemCadastro(), familiaComAlteracoes.getFamiliaresSemCadastro());
        Assertions.assertEquals(familia.getEndereco().getCep(), familiaComAlteracoes.getEndereco().getCep());
    }

    @Test
    @Transactional
    void naoDeveCadastrarNovoEnderecoQuandoEditarFamilia() throws Exception {
        Familia familia = service.save(modelMapper.map(familiaDTO, Familia.class));
        Long enderecoId = familia.getEndereco().getId();
        String cep = familia.getEndereco().getCep();

        Familia familiaComAlteracoes = SerializationUtils.clone(familia);
        familiaComAlteracoes.getEndereco().setCep("000000000");
        service.edit(familiaComAlteracoes);

        Assertions.assertNotEquals(familia.getEndereco().getCep(), cep);
        Assertions.assertEquals(familia.getEndereco().getId(), enderecoId);
    }

    @Test
    @Transactional
    void deveConsultarFamilias(){
        Assertions.assertTrue(service.findAll().isEmpty());

        service.save(modelMapper.map(familiaDTO, Familia.class));
        service.save(modelMapper.map(familiaDTO, Familia.class));
        service.save(modelMapper.map(familiaDTO, Familia.class));

        Assertions.assertEquals(3, service.findAll().size());
    }

    @Test
    @Transactional
    void deveConsultarFamiliaPorId() throws Exception {
        Familia familiaSalva = service.save(modelMapper.map(familiaDTO, Familia.class));
        Assertions.assertEquals(service.findById(familiaSalva.getId()),familiaSalva);
    }

    @Test
    @Transactional
    void deveExcluirFamiliaPorId() {
        Familia familiaSalva = service.save(modelMapper.map(familiaDTO, Familia.class));
        service.delete(familiaSalva.getId());
        Assertions.assertEquals(0, service.findAll().size());
    }

    @Test
    @Transactional
    void deveExcluirEnderecoAoExcluirFamilia() {
        Familia familiaSalva = service.save(modelMapper.map(familiaDTO, Familia.class));
        Assertions.assertEquals(1, enderecoRepository.findAll().size());

        service.delete(familiaSalva.getId());
        Assertions.assertEquals(0, enderecoRepository.findAll().size());
    }

}
