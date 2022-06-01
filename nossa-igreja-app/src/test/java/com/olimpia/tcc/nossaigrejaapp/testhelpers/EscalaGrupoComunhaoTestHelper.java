package com.olimpia.tcc.nossaigrejaapp.testhelpers;

import com.olimpia.tcc.nossaigrejaapp.dto.GrupoComunhaoDTO;
import com.olimpia.tcc.nossaigrejaapp.dto.UserDTO;
import com.olimpia.tcc.nossaigrejaapp.model.EscalaGrupoComunhao;
import com.olimpia.tcc.nossaigrejaapp.model.GrupoComunhao;
import com.olimpia.tcc.nossaigrejaapp.model.User;
import com.olimpia.tcc.nossaigrejaapp.repository.UserRepository;
import com.olimpia.tcc.nossaigrejaapp.service.GrupoComunhaoService;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@UtilityClass
public class EscalaGrupoComunhaoTestHelper {

    public EscalaGrupoComunhao createEscala(GrupoComunhaoService grupoComunhaoService, UserRepository userRepository, ModelMapper mapper){
        EscalaGrupoComunhao escala = new EscalaGrupoComunhao();
        escala.setData(LocalDate.now());
        escala.setGrupoComunhao(getGrupoComunhao(grupoComunhaoService, userRepository, mapper));
        escala.setDinamica(getUsuario(userRepository, mapper));
        escala.setLouvor(getUsuario(userRepository, mapper));
        escala.setPalavra(getUsuario(userRepository, mapper));
        escala.setLanche(getUsuario(userRepository, mapper));

        return escala;
    }

    private GrupoComunhao getGrupoComunhao(GrupoComunhaoService grupoComunhaoService, UserRepository userRepository, ModelMapper mapper){
        GrupoComunhaoDTO grupoDTO = GrupoComunhaoTestHelper.createDTO(userRepository, mapper);
        return grupoComunhaoService.save(mapper.map(grupoDTO, GrupoComunhao.class));
    }

    private User getUsuario(UserRepository userRepository, ModelMapper mapper){
        UserDTO usuarioDTO = UsuarioTestHelper.createUserDTO();
        return userRepository.save(mapper.map(usuarioDTO, User.class));
    }
}
