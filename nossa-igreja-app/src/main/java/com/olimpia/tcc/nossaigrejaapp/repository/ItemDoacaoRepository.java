package com.olimpia.tcc.nossaigrejaapp.repository;

import com.olimpia.tcc.nossaigrejaapp.model.ItemDoacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDoacaoRepository extends JpaRepository<ItemDoacao, Long> {

    List<ItemDoacao> findByCategoriaIdAndNomeStartingWithIgnoreCaseOrderByNome(Long id, String prefix);

    List<ItemDoacao> findByNomeStartingWithIgnoreCaseOrderByNome(String prefix);

}
