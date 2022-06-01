package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.dto.DoacaoDTO;
import com.olimpia.tcc.nossaigrejaapp.model.Doacao;
import com.olimpia.tcc.nossaigrejaapp.repository.*;
import com.olimpia.tcc.nossaigrejaapp.testhelpers.DoacaoTestHelper;
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
import java.math.BigInteger;
import java.time.LocalDate;

@ActiveProfiles("test")
@SpringBootTest
public class DoacaoServiceIntegracaoTest {

    @Autowired
    private DoacaoService service;

    @Autowired
    private ItemDoacaoService itemDoacaoService;

    @Autowired
    private FamiliaService familiaService;

    @Autowired
    private CategoriaDoacaoRepository categoriaDoacaoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private DoacaoDTO doacaoDTO;

    @BeforeEach
    public void setup(){
        doacaoDTO = DoacaoTestHelper.createDTO(
                itemDoacaoService,
                familiaService,
                categoriaDoacaoRepository,
                userRepository,
                modelMapper
        );
    }

    @AfterEach
    public void tearDown(){
        service.deleteAll();
        itemDoacaoService.deleteAll();
        familiaService.deleteAll();
        categoriaDoacaoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void deveCadastrarDoacao() {
        Doacao doacao = modelMapper.map(doacaoDTO, Doacao.class);
        Doacao doacaoSalva = service.save(doacao);

        Assertions.assertEquals(doacao.getItem(), doacaoSalva.getItem());
        Assertions.assertEquals(doacao.getTamanho(), doacaoSalva.getTamanho());
        Assertions.assertEquals(doacao.getQuantidade(), doacaoSalva.getQuantidade());
        Assertions.assertEquals(doacao.getFamilia(), doacaoSalva.getFamilia());
    }

    @Test
    @Transactional
    void deveAlterarDoacao() {
        Doacao doacao = service.save(modelMapper.map(doacaoDTO, Doacao.class));

        Doacao doacaoComAlteracoes = SerializationUtils.clone(doacao);
        doacaoComAlteracoes.setQuantidade(BigInteger.TEN);
        doacaoComAlteracoes.setTamanho("Novo tamanho");

        service.edit(doacaoComAlteracoes);

        Assertions.assertEquals(doacao.getQuantidade(), doacaoComAlteracoes.getQuantidade());
        Assertions.assertEquals(doacao.getTamanho(), doacaoComAlteracoes.getTamanho());
    }

    @Test
    @Transactional
    void deveConsultarDoacao(){
        Assertions.assertTrue(service.findAll().isEmpty());

        service.save(modelMapper.map(doacaoDTO, Doacao.class));
        service.save(modelMapper.map(doacaoDTO, Doacao.class));
        service.save(modelMapper.map(doacaoDTO, Doacao.class));

        Assertions.assertEquals(3, service.findAll().size());
    }

    @Test
    @Transactional
    void deveConsultarApenasPedidosDeDoacaoDisponiveis(){
        service.save(modelMapper.map(doacaoDTO, Doacao.class));
        service.save(modelMapper.map(doacaoDTO, Doacao.class));

        Doacao doacaoReservada = SerializationUtils.clone(modelMapper.map(doacaoDTO, Doacao.class));
        doacaoReservada.setDataEntrega(LocalDate.now());
        service.save(doacaoReservada);

        Assertions.assertEquals(2, service.findAllDisponivelDoacao().size());
    }


    @Test
    @Transactional
    void deveConsultarDoacaoPorId() throws Exception {
        Doacao doacaoSalva = service.save(modelMapper.map(doacaoDTO, Doacao.class));
        Assertions.assertEquals(service.findById(doacaoSalva.getId()),doacaoSalva);
    }

    @Test
    @Transactional
    void deveExcluirDoacaoPorId() {
        Doacao doacaoSalva = service.save(modelMapper.map(doacaoDTO, Doacao.class));
        service.delete(doacaoSalva.getId());
        Assertions.assertEquals(0, service.findAll().size());
    }

    @Test
    @Transactional
    void deveAlterarEntregaRealizadaParaTrueQuandoFalse() throws Exception{
        Doacao doacaoSalva = service.save(modelMapper.map(doacaoDTO, Doacao.class));
        Assertions.assertFalse(doacaoSalva.isEntregaRealizada());

        service.alterarStatusRecebimento(doacaoSalva.getId());
        Assertions.assertTrue(doacaoSalva.isEntregaRealizada());
    }

    @Test
    @Transactional
    void deveAlterarEntregaRealizadaParaFalseQuandoTrue() throws Exception{
        doacaoDTO.setEntregaRealizada(Boolean.TRUE);
        Doacao doacaoSalva = service.save(modelMapper.map(doacaoDTO, Doacao.class));
        Assertions.assertTrue(doacaoSalva.isEntregaRealizada());

        service.alterarStatusRecebimento(doacaoSalva.getId());
        Assertions.assertFalse(doacaoSalva.isEntregaRealizada());
    }

    @Test
    @Transactional
    void deveCriarNovaDoacaoSeQuantidadeReservadaMenorQueQuantidade() throws Exception{
        Doacao doacao = service.save(modelMapper.map(doacaoDTO, Doacao.class));
        Assertions.assertEquals(1, service.findAll().size());

        Doacao doacaoReservada = SerializationUtils.clone(doacao);
        doacaoReservada.setQuantidadeReservada(doacao.getQuantidade().subtract(BigInteger.ONE));
        service.doar(doacaoReservada);
        Assertions.assertEquals(2,service.findAll().size());

    }

    @Test
    @Transactional
    void naoDeveCriarNovaDoacaoSeQuantidadeReservadaIgualQuantidade() throws Exception{
        Doacao doacao = service.save(modelMapper.map(doacaoDTO, Doacao.class));
        Assertions.assertEquals(1, service.findAll().size());

        Doacao doacaoReservada = SerializationUtils.clone(doacao);
        doacaoReservada.setQuantidadeReservada(doacao.getQuantidade());
        service.doar(doacaoReservada);
        Assertions.assertEquals(1,service.findAll().size());
    }
}
