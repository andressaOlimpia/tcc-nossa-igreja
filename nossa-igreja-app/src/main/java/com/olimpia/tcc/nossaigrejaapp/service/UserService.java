package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.model.RolesEnum;
import com.olimpia.tcc.nossaigrejaapp.model.User;
import com.olimpia.tcc.nossaigrejaapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserValidationService userValidationService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User save(User usuario) throws Exception {
        userValidationService.validaSeEmailJaCadastrado(usuario.getEmail());
        usuario.setUsername(usuario.getEmail());
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        usuario.setEnabled(Boolean.TRUE);
        usuario.setCreatedDate(LocalDateTime.now());
        roleService.setRole(usuario, RolesEnum.ROLE_VISITANTE.name());

        return userRepository.save(usuario);
    }

    public User findById(Long id){return userRepository.findById(id).orElseThrow();}

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public List<User> findAllFilteredByFirstName(String prefix){
        return userRepository.findByFirstNameStartingWithIgnoreCaseOrderByFirstName(prefix);
    }
}
