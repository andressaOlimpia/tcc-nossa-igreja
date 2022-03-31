package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserValidationService {

    private final UserRepository repository;

    public void validaSeEmailJaCadastrado(String email) throws Exception {
       boolean emailCadastrado = repository.findByUsername(email).isPresent();
       if(emailCadastrado){
           throw new Exception("Usuário com esse email já existente");
       }
    }
}
