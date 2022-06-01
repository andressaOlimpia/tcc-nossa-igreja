package com.olimpia.tcc.nossaigrejaapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(
        name = "ESCALA_GRUPO_COMUNHAO",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ID_GRUPO_COMUNHAO", "DATA"})
        })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "hashcode"})
@SequenceGenerator(name = "ESCALA_GRUPO_COMUNHAO_ID_ESCALA_GRUPO_COMUNHAO_SEQ", allocationSize = 1)
public class EscalaGrupoComunhao implements Serializable {

    @Id
    @GeneratedValue(generator = "ESCALA_GRUPO_COMUNHAO_ID_ESCALA_GRUPO_COMUNHAO_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_ESCALA_GRUPO_COMUNHAO")
    private Long id;

    @NotNull
    @Column(name = "DATA")
    private LocalDate data;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ID_GRUPO_COMUNHAO", foreignKey = @ForeignKey(name = "FK_ESCALA_GRUPO_COMUNHAO_GRUPO"))
    private GrupoComunhao grupoComunhao;

    @ManyToOne
    @JoinColumn(name = "ID_USER_DINAMICA", referencedColumnName="ID_USER", foreignKey = @ForeignKey(name = "FK_ESCALA_GRUPO_COMUNHAO_USER_DINAMICA"))
    private User dinamica;

    @ManyToOne
    @JoinColumn(name = "ID_USER_LOUVOR", referencedColumnName="ID_USER", foreignKey = @ForeignKey(name = "FK_ESCALA_GRUPO_COMUNHAO_USER_LOUVOR"))
    private User louvor;

    @ManyToOne
    @JoinColumn(name = "ID_USER_PALAVRA", referencedColumnName="ID_USER", foreignKey = @ForeignKey(name = "FK_ESCALA_GRUPO_COMUNHAO_USER_PALAVRA"))
    private User palavra;

    @ManyToOne
    @JoinColumn(name = "ID_USER_LANCHE", referencedColumnName="ID_USER", foreignKey = @ForeignKey(name = "FK_ESCALA_GRUPO_COMUNHAO_USER_LANCHE"))
    private User lanche;
}
