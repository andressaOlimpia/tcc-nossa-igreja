package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.model.PessoaSemCadastro;
import com.olimpia.tcc.nossaigrejaapp.repository.PessoaSemCadastroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PessoaSemCadastroService {

    private final PessoaSemCadastroRepository repository;

    public PessoaSemCadastro save(PessoaSemCadastro PessoaSemCadastro){
        return repository.save(PessoaSemCadastro);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

}
