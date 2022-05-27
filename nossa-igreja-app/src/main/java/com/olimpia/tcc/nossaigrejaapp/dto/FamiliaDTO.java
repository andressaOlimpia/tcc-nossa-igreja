package com.olimpia.tcc.nossaigrejaapp.dto;

import com.olimpia.tcc.nossaigrejaapp.model.Endereco;
import com.olimpia.tcc.nossaigrejaapp.model.PessoaSemCadastro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamiliaDTO {

    private Long id;

    @NotNull
    private UserDTO principal;

    private List<UserDTO> familiares;

    private List<PessoaSemCadastro> familiaresSemCadastro;

    private Endereco endereco;

}
