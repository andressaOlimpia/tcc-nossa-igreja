package com.olimpia.tcc.nossaigrejaapp.repository;

import com.olimpia.tcc.nossaigrejaapp.model.EscalaGrupoComunhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EscalaGrupoComunhaoRepository extends JpaRepository<EscalaGrupoComunhao, Long> {

    List<EscalaGrupoComunhao> findByGrupoComunhaoIdAndDataGreaterThanEqualOrderByData(Long participanteId, LocalDate diaAtual);
}
