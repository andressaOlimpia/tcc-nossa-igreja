package com.olimpia.tcc.nossaigrejaapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "GRUPO_COMUNHAO")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "hashcode"})
@SequenceGenerator(name = "GRUPO_COMUNHAO_ID_GRUPO_COMUNHAO_SEQ", allocationSize = 1)
public class GrupoComunhao implements Serializable {

    @Id
    @GeneratedValue(generator = "GRUPO_COMUNHAO_ID_GRUPO_COMUNHAO_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_GRUPO_COMUNHAO")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @NotNull
    @Column(name = "DIA_SEMANA")
    private DiaSemanaEnum diaSemana;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="HH:mm")
    @NotNull
    @Column(name = "HORARIO")
    private LocalTime horario;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinTable(name = "GRUPO_COMUNHAO_ENDERECO",
//            joinColumns = { @JoinColumn(name = "ID_GRUPO_GOMUNHAO") },
//            inverseJoinColumns = { @JoinColumn(name = "ID_ENDERECO") })
//    private List<Endereco> enderecos;

    @OneToOne
    @JoinColumn(name = "ID_ENDERECO", foreignKey = @ForeignKey(name = "FK_GRUPO_COMUNHAO_ENDERECO"))
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "ID_USER", foreignKey = @ForeignKey(name = "FK_GRUPO_COMUNHAO_LIDER"))
    private User lider;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "GRUPO_COMUNHAO_PARTICIPANTES",
            joinColumns = @JoinColumn(name = "ID_GRUPO_COMUNHAO"),
            inverseJoinColumns = @JoinColumn(name= "ID_USER"))
    private List<User> participantes;

    @Column(name = "MAX_PARTICIPANTES")
    private BigInteger maxParticipantes;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
}
