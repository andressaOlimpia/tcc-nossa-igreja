package com.olimpia.tcc.nossaigrejaapp.testhelpers;

import com.olimpia.tcc.nossaigrejaapp.model.Endereco;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EnderecoTestHelper {

    public Endereco createEndereco(){
        Endereco endereco = new Endereco();
        endereco.setCep("88305-600");
        endereco.setLogradouro("Rua Leodegário Pedro da Silva");
        endereco.setNumero("1");
        endereco.setComplemento("teste");
        endereco.setBairro("Barra do Rio");
        endereco.setCidade("Itajaí");
        endereco.setEstado("SC");

        return endereco;
    }
}
