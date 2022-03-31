package com.olimpia.tcc.nossaigrejaapp.repository;

import com.olimpia.tcc.nossaigrejaapp.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
