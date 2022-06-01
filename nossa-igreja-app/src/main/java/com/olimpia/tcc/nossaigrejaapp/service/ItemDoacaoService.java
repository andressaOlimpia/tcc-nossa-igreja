package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.model.ItemDoacao;
import com.olimpia.tcc.nossaigrejaapp.repository.ItemDoacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ItemDoacaoService {

    private final ItemDoacaoRepository repository;

    public ItemDoacao save(ItemDoacao itemDoacao){
        return repository.save(itemDoacao);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public void deleteAll() {repository.deleteAll();}

    public List<ItemDoacao> findAllFilteredByCategoriaENome(Long categoriaId, String prefixoNome){
        return categoriaId == null? repository.findByNomeStartingWithIgnoreCaseOrderByNome(prefixoNome)
        : repository.findByCategoriaIdAndNomeStartingWithIgnoreCaseOrderByNome(categoriaId, prefixoNome);
    }
}
