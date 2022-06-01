package com.olimpia.tcc.nossaigrejaapp.testhelpers;

import com.olimpia.tcc.nossaigrejaapp.model.CategoriaDoacao;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoriaDoacaoTestHelper {

    public CategoriaDoacao createCategoriaDoacao(){
        CategoriaDoacao categoriaDoacao = new CategoriaDoacao();
        categoriaDoacao.setNome("Categoria teste");

        return categoriaDoacao;
    }
}
