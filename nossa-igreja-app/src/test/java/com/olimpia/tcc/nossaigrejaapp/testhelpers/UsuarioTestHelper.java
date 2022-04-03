package com.olimpia.tcc.nossaigrejaapp.testhelpers;

import com.olimpia.tcc.nossaigrejaapp.dto.UserDTO;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class UsuarioTestHelper {

    public UserDTO createUserDTO(){
        return UserDTO.builder()
                .firstName("Nome")
                .lastName("sobrenome")
                .birthDate(LocalDate.now())
                .phoneNumber("99999-9999")
                .email("teste@teste.com")
                .build();
    }

}
