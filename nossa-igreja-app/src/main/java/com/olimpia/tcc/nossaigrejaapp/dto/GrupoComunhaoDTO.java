package com.olimpia.tcc.nossaigrejaapp.dto;

import com.olimpia.tcc.nossaigrejaapp.model.DiaSemanaEnum;
import com.olimpia.tcc.nossaigrejaapp.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoComunhaoDTO {

    private Long id;

    private String nome;

    @NotNull
    private DiaSemanaEnum diaSemana;

    @NotNull
    private LocalTime horario;

    @NotNull
    private Endereco endereco;

    private UserDTO lider;

    private List<UserDTO> participantes;

    private BigInteger maxParticipantes;
}
