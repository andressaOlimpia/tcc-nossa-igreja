package com.olimpia.tcc.nossaigrejaapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "FAMILIA")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "hashcode"})
@SequenceGenerator(name = "FAMILIA_ID_FAMILIA_SEQ", allocationSize = 1)
public class Familia {

    @Id
    @GeneratedValue(generator = "FAMILIA_ID_FAMILIA_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_FAMILIA")
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "ID_USER", foreignKey = @ForeignKey(name = "FK_FAMILIA_USER_PRINCIPAL"))
    private User principal;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "FAMILIA_USER",
            joinColumns = @JoinColumn(name = "ID_FAMILIA"),
            inverseJoinColumns = @JoinColumn(name= "ID_USER"))
    private List<User> familiares = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "FAMILIA_PESSOA_SEM_CADASTRO",
            joinColumns =  @JoinColumn(name = "ID_FAMILIA"),
            inverseJoinColumns = @JoinColumn(name = "ID_PESSOA_SEM_CADASTRO"))
    private List<PessoaSemCadastro> familiaresSemCadastro;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "ID_ENDERECO", foreignKey = @ForeignKey(name = "FK_FAMILIA_ENDERECO"))
    private Endereco endereco;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
