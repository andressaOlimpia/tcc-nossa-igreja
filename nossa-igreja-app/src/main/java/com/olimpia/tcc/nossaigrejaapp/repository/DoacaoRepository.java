package com.olimpia.tcc.nossaigrejaapp.repository;

import com.olimpia.tcc.nossaigrejaapp.model.Doacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DoacaoRepository extends JpaRepository<Doacao, Long> {

    List<Doacao> findByDataEntregaIsNull();

    List<Doacao> findByCreatedDateGreaterThanEqual(LocalDateTime dataCalculo);
}
