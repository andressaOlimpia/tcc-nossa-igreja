package com.olimpia.tcc.nossaigrejaapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "DOACAO")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "hashcode"})
@SequenceGenerator(name = "DOACAO_ID_DOACAO_SEQ", allocationSize = 1)
public class Doacao implements Serializable {

    @Id
    @GeneratedValue(generator = "DOACAO_ID_DOACAO_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_DOACAO")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ID_ITEM_DOACAO", foreignKey = @ForeignKey(name = "FK_DOACAO_ITEM_DOACAO"))
    private ItemDoacao item;

    @NotBlank
    @Column(name = "TAMANHO")
    private String tamanho;

    @NotNull
    @Column(name = "QUANTIDADE")
    private BigInteger quantidade;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ID_FAMILIA", foreignKey = @ForeignKey(name = "FK_DOACAO_FAMILIA"))
    private Familia familia;

    @Column(name = "DETALHES")
    private String detalhes;

    @ManyToOne
    @JoinColumn(name = "ID_USER", foreignKey = @ForeignKey(name = "FK_DOACAO_DOADOR"))
    private User doador;

    @Column(name = "DATA_RESERVA")
    private LocalDate dataReserva;

    @Column(name = "QUANTIDADE_RESERVADA")
    private BigInteger quantidadeReservada;

    @Column(name = "DATA_ENTREGA")
    private LocalDate dataEntrega;

    @Column(name = "ENTREGA_REALIZADA")
    private boolean entregaRealizada = Boolean.FALSE;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
}
