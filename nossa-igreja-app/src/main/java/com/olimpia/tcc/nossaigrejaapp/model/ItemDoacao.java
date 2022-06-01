package com.olimpia.tcc.nossaigrejaapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "ITEM_DOACAO")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "hashcode"})
@SequenceGenerator(name = "ITEM_DOACAO_ID_ITEM_DOACAO_SEQ", allocationSize = 1)
public class ItemDoacao implements Serializable {

    @Id
    @GeneratedValue(generator = "ITEM_DOACAO_ID_ITEM_DOACAO_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_ITEM_DOACAO")
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "ID_CATEGORIA_DOACAO", foreignKey = @ForeignKey(name = "FK_ITEM_DOACAO_CATEGORIA"))
    private CategoriaDoacao categoria;
}
