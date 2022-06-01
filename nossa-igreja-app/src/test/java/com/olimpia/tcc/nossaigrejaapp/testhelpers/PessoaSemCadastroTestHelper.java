package com.olimpia.tcc.nossaigrejaapp.testhelpers;

import com.olimpia.tcc.nossaigrejaapp.model.PessoaSemCadastro;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class PessoaSemCadastroTestHelper {

    public PessoaSemCadastro createPessoaSemCadastro(){
        PessoaSemCadastro pessoaSemCadastro = new PessoaSemCadastro();
        pessoaSemCadastro.setNome("Nome");
        pessoaSemCadastro.setSobrenome("Sobrenome");
        pessoaSemCadastro.setDataNascimento(LocalDate.now());

        return pessoaSemCadastro;
    }
}
