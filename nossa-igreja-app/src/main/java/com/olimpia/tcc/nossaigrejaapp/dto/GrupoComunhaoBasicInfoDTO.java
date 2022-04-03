package com.olimpia.tcc.nossaigrejaapp.dto;

import com.olimpia.tcc.nossaigrejaapp.model.DiaSemanaEnum;
import com.olimpia.tcc.nossaigrejaapp.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoComunhaoBasicInfoDTO {

    private Long id;

    private String nome;

    private DiaSemanaEnum diaSemana;

    private LocalTime horario;

    private UserDTO lider;
}
