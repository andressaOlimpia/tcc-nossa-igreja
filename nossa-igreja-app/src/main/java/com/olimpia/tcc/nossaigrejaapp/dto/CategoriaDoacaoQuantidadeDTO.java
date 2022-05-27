package com.olimpia.tcc.nossaigrejaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDoacaoQuantidadeDTO {

    private String categoria;

    private BigInteger quantidade;
}
