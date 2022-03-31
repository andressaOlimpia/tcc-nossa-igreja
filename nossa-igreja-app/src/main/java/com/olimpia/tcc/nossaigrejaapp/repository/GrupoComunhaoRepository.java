package com.olimpia.tcc.nossaigrejaapp.repository;

import com.olimpia.tcc.nossaigrejaapp.model.GrupoComunhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoComunhaoRepository  extends JpaRepository<GrupoComunhao, Long> {
}
