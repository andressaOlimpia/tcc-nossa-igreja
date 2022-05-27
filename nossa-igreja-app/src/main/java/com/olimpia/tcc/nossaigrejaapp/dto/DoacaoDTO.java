package com.olimpia.tcc.nossaigrejaapp.dto;

import com.olimpia.tcc.nossaigrejaapp.model.ItemDoacao;
import com.olimpia.tcc.nossaigrejaapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoacaoDTO {

    private Long id;

    @NotNull
    private ItemDoacao item;

    @NotBlank
    private String tamanho;

    @NotNull
    private BigInteger quantidade;

    @NotNull
    private FamiliaDTO familia;

    private String detalhes;

    private UserDTO doador;

    private LocalDate dataReserva;

    private BigInteger quantidadeReservada;

    private LocalDate dataEntrega;

    private Boolean entregaRealizada;
}
