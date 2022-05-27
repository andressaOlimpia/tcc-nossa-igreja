package com.olimpia.tcc.nossaigrejaapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "CATEGORIA_DOACAO")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "hashcode"})
@SequenceGenerator(name = "CATEGORIA_DOACAO_ID_CATEGORIA_DOACAO_SEQ", allocationSize = 1)
public class CategoriaDoacao {

    @Id
    @GeneratedValue(generator = "CATEGORIA_DOACAO_ID_CATEGORIA_DOACAO_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_CATEGORIA_DOACAO")
    private Long id;

    @Column(name = "NOME")
    private String nome;
}
