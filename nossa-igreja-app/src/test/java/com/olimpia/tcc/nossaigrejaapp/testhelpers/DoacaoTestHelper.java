package com.olimpia.tcc.nossaigrejaapp.testhelpers;

import com.olimpia.tcc.nossaigrejaapp.dto.DoacaoDTO;
import com.olimpia.tcc.nossaigrejaapp.dto.FamiliaDTO;
import com.olimpia.tcc.nossaigrejaapp.model.Familia;
import com.olimpia.tcc.nossaigrejaapp.model.ItemDoacao;
import com.olimpia.tcc.nossaigrejaapp.repository.CategoriaDoacaoRepository;
import com.olimpia.tcc.nossaigrejaapp.repository.UserRepository;
import com.olimpia.tcc.nossaigrejaapp.service.FamiliaService;
import com.olimpia.tcc.nossaigrejaapp.service.ItemDoacaoService;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

import java.math.BigInteger;

@UtilityClass
public class DoacaoTestHelper {

    public DoacaoDTO createDTO(ItemDoacaoService itemDoacaoService,
                               FamiliaService familiaService,
                               CategoriaDoacaoRepository categoriaDoacaoRepository,
                               UserRepository userRepository,
                               ModelMapper mapper){

        return DoacaoDTO.builder()
                .item(getItemDoacao(categoriaDoacaoRepository, itemDoacaoService))
                .tamanho("P")
                .quantidade(BigInteger.TWO)
                .familia(getFamiliaDTO(userRepository,mapper,familiaService))
                .build();
    }

    private ItemDoacao getItemDoacao(CategoriaDoacaoRepository categoriaDoacaoRepository, ItemDoacaoService itemDoacaoService){
        ItemDoacao item = ItemDoacaoTestHelper.createItemDoacao(categoriaDoacaoRepository);
        return itemDoacaoService.save(item);
    }

    private FamiliaDTO getFamiliaDTO(UserRepository userRepository, ModelMapper mapper, FamiliaService familiaService){
        Familia familia = mapper.map(FamiliaTestHelper.createFamiliaDTO(userRepository, mapper), Familia.class);
        return mapper.map(familiaService.save(familia), FamiliaDTO.class);
    }
}
