package com.olimpia.tcc.nossaigrejaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EscalaGrupoComunhaoDTO {

    private Long id;

    @NotNull
    private LocalDate data;

    @NotNull
    private GrupoComunhaoDTO grupoComunhao;

    private UserDTO dinamica;

    private UserDTO louvor;

    private UserDTO palavra;

    private UserDTO lanche;
}
