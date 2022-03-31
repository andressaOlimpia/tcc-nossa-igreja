package com.olimpia.tcc.nossaigrejaapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ENDERECO")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "hashcode"})
@SequenceGenerator(name = "ENDERECO_ID_ENDERECO_SEQ", allocationSize = 1)
public class Endereco implements Serializable {

    @Id
    @GeneratedValue(generator = "ENDERECO_ID_ENDERECO_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_ENDERECO")
    private Long id;

    @NotNull
    @Column(name = "CEP")
    private String cep;

    @NotNull
    @Column(name = "LOGRADOURO")
    private String logradouro;

    @NotNull
    @Column(name = "NUMERO")
    private String numero;

    @Column(name = "COMPLEMENTO")
    private String complemento;

    @NotNull
    @Column(name = "BAIRRO")
    private String bairro;

    @NotNull
    @Column(name = "CIDADE")
    private String cidade;

    @NotNull
    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
