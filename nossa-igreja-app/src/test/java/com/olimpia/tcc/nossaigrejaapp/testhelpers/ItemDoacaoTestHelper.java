package com.olimpia.tcc.nossaigrejaapp.testhelpers;

import com.olimpia.tcc.nossaigrejaapp.model.ItemDoacao;
import com.olimpia.tcc.nossaigrejaapp.repository.CategoriaDoacaoRepository;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemDoacaoTestHelper {

    public ItemDoacao createItemDoacao(CategoriaDoacaoRepository categoriaDoacaoRepository){
        ItemDoacao itemDoacao = new ItemDoacao();

        itemDoacao.setNome("item teste");
        itemDoacao.setCategoria(categoriaDoacaoRepository.save(CategoriaDoacaoTestHelper.createCategoriaDoacao()));

        return itemDoacao;
    }
}
