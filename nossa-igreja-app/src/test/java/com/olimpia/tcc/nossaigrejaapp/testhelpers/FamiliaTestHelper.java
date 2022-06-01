package com.olimpia.tcc.nossaigrejaapp.testhelpers;

import com.olimpia.tcc.nossaigrejaapp.dto.FamiliaDTO;
import com.olimpia.tcc.nossaigrejaapp.dto.UserDTO;
import com.olimpia.tcc.nossaigrejaapp.model.User;
import com.olimpia.tcc.nossaigrejaapp.repository.UserRepository;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

import java.util.List;

@UtilityClass
public class FamiliaTestHelper {

    public FamiliaDTO createFamiliaDTO(UserRepository userRepository, ModelMapper mapper){
        return FamiliaDTO.builder()
                .principal(getUsuario(userRepository, mapper))
                .familiares(List.of(getUsuario(userRepository, mapper)))
                .familiaresSemCadastro(List.of(PessoaSemCadastroTestHelper.createPessoaSemCadastro()))
                .endereco(EnderecoTestHelper.createEndereco())
                .build();
    }

    private UserDTO getUsuario(UserRepository userRepository, ModelMapper mapper){
        UserDTO usuarioDTO = UsuarioTestHelper.createUserDTO();
        User usuarioSalvo = userRepository.save(mapper.map(usuarioDTO, User.class));
        usuarioDTO.setId(usuarioSalvo.getId());

        return usuarioDTO;
    }
}
