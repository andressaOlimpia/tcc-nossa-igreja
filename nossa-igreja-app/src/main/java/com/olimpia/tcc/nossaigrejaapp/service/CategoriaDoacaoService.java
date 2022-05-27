package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.model.CategoriaDoacao;
import com.olimpia.tcc.nossaigrejaapp.repository.CategoriaDoacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoriaDoacaoService {

    private final CategoriaDoacaoRepository repository;

    public CategoriaDoacao save(CategoriaDoacao categoriaDoacao){
        return repository.save(categoriaDoacao);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public List<CategoriaDoacao> findAllFilteredByNome(String prefix){
        return repository.findByNomeStartingWithIgnoreCaseOrderByNome(prefix);
    }
}
