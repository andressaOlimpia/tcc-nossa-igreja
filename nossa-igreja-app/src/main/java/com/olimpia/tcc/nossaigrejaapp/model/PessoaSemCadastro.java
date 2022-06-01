package com.olimpia.tcc.nossaigrejaapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "PESSOA_SEM_CADASTRO")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "hashcode"})
@SequenceGenerator(name = "PESSOA_SEM_CADASTRO_ID_PESSOA_SEM_CADASTRO_SEQ", allocationSize = 1)
public class PessoaSemCadastro implements Serializable {

    @Id
    @GeneratedValue(generator = "PESSOA_SEM_CADASTRO_ID_PESSOA_SEM_CADASTRO_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_PESSOA_SEM_CADASTRO")
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "SOBRENOME")
    private String sobrenome;

    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;
}
