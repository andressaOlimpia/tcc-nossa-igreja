package com.olimpia.tcc.nossaigrejaapp.testhelpers;

import com.olimpia.tcc.nossaigrejaapp.dto.GrupoComunhaoDTO;
import com.olimpia.tcc.nossaigrejaapp.dto.UserDTO;
import com.olimpia.tcc.nossaigrejaapp.model.DiaSemanaEnum;
import com.olimpia.tcc.nossaigrejaapp.model.User;
import com.olimpia.tcc.nossaigrejaapp.repository.UserRepository;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

import java.math.BigInteger;
import java.time.LocalTime;
import java.util.List;

@UtilityClass
public class GrupoComunhaoTestHelper {

    public GrupoComunhaoDTO createDTO(UserRepository userRepository, ModelMapper mapper){
        return GrupoComunhaoDTO.builder()
                .diaSemana(DiaSemanaEnum.DOM)
                .horario(LocalTime.now())
                .nome("Nome teste")
                .endereco(EnderecoTestHelper.createEndereco())
                .lider(getUsuario(userRepository, mapper))
                .participantes(List.of(getUsuario(userRepository, mapper)))
                .maxParticipantes(BigInteger.TEN)
                .build();
    }

    private UserDTO getUsuario(UserRepository userRepository, ModelMapper mapper){
        UserDTO usuarioDTO = UsuarioTestHelper.createUserDTO();
        User usuarioSalvo = userRepository.save(mapper.map(usuarioDTO, User.class));
        usuarioDTO.setId(usuarioSalvo.getId());

        return usuarioDTO;
    }

}
