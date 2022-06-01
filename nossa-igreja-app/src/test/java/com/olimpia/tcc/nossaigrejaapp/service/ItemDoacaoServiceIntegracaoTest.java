package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.model.ItemDoacao;
import com.olimpia.tcc.nossaigrejaapp.repository.CategoriaDoacaoRepository;
import com.olimpia.tcc.nossaigrejaapp.repository.ItemDoacaoRepository;
import com.olimpia.tcc.nossaigrejaapp.testhelpers.ItemDoacaoTestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public class ItemDoacaoServiceIntegracaoTest {

    @Autowired
    private ItemDoacaoService service;

    @Autowired
    private ItemDoacaoRepository repository;

    @Autowired
    private CategoriaDoacaoRepository categoriaDoacaoRepository;

    private ItemDoacao itemDoacao;

    @BeforeEach
    public void setup(){
        itemDoacao = service.save(ItemDoacaoTestHelper.createItemDoacao(categoriaDoacaoRepository));
    }

    @AfterEach
    public void tearDown(){
        repository.deleteAll();
        categoriaDoacaoRepository.deleteAll();
    }

    @Test
    void deveRetornarItemComCategoriaENomeInformados(){
        Long categoriaId = itemDoacao.getCategoria().getId();
        String pedacoNome = itemDoacao.getNome().substring(0,2);

        List<ItemDoacao> itensRetornados = service.findAllFilteredByCategoriaENome(categoriaId, pedacoNome);
        Assertions.assertEquals(1, itensRetornados.size());
        Assertions.assertEquals(itemDoacao.getId(), itensRetornados.get(0).getId());
    }

    @Test
    void naoDeveRetornarItemComOutraCategoria(){
        Long categoriaId = itemDoacao.getCategoria().getId() + 1;
        String pedacoNome = itemDoacao.getNome().substring(0,2);

        List<ItemDoacao> itensRetornados = service.findAllFilteredByCategoriaENome(categoriaId, pedacoNome);
        Assertions.assertEquals(0, itensRetornados.size());
    }

    @Test
    void naoDeveRetornarItemComOutroNome(){
        Long categoriaId = itemDoacao.getCategoria().getId() + 1;
        String pedacoNome = "outro";

        List<ItemDoacao> itensRetornados = service.findAllFilteredByCategoriaENome(categoriaId, pedacoNome);
        Assertions.assertEquals(0, itensRetornados.size());
    }
}
