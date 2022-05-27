package com.olimpia.tcc.nossaigrejaapp.model;

public enum MesesEnum {
    JAN("Janeiro"),
    FEV("Fevereiro"),
    MAR("Março"),
    ABR("Abril"),
    MAI("Maio"),
    JUN("Junho"),
    JUL("Julho"),
    AGO("Agosto"),
    SET("Setembro"),
    OUT("Outubro"),
    NOV("Novembro"),
    DEZ("Dezembro");

    private final String descricao;

    MesesEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
