package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.model.Endereco;
import com.olimpia.tcc.nossaigrejaapp.model.GrupoComunhao;
import com.olimpia.tcc.nossaigrejaapp.repository.GrupoComunhaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class GrupoComunhaoService {

    private final GrupoComunhaoRepository grupoComunhaoRepository;
    private final EnderecoService enderecoService;

    public GrupoComunhao save(GrupoComunhao grupoComunhao) {
        grupoComunhao.setEndereco(getEnderecosSalvos(List.of(grupoComunhao.getEndereco())).get(0));
        grupoComunhao.setCreatedDate(LocalDateTime.now());

        return grupoComunhaoRepository.save(grupoComunhao);
    }

    private List<Endereco> getEnderecosSalvos(List<Endereco> enderecos){
        List<Endereco> enderecosGrupoComunhao = new ArrayList<>();
        enderecos.forEach(endereco -> enderecosGrupoComunhao.add(enderecoService.save(endereco)));

        return enderecosGrupoComunhao;
    }
}
