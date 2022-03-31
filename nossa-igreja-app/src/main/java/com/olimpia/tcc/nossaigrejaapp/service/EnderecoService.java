package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.model.Endereco;
import com.olimpia.tcc.nossaigrejaapp.repository.EnderecoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public Endereco save(Endereco endereco){
        endereco.setCreatedDate(LocalDateTime.now());
        return enderecoRepository.save(endereco);
    }
}
