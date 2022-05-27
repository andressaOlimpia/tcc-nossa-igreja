package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.dto.UserDTO;
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

    public User edit(UserDTO userDTO) throws Exception {
        User usuario = findById(userDTO.getId());

        usuario.setFirstName(userDTO.getFirstName());
        usuario.setLastName(userDTO.getLastName());
        usuario.setBirthDate(userDTO.getBirthDate());
        usuario.setPhoneNumber(userDTO.getPhoneNumber());
        usuario.setEmail(userDTO.getEmail());
        usuario.setUsername(userDTO.getEmail());

        return userRepository.save(usuario);
    }

    public User findById(Long id) throws Exception {return userRepository.findById(id)
            .orElseThrow(()-> new Exception("Usuário não encontrado"));}


    public List<User> findAllFilteredByFirstName(String prefix){
        return userRepository.findByFirstNameStartingWithIgnoreCaseOrderByFirstName(prefix);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public void deleteAll(List<Long> ids){
        userRepository.deleteAllById(ids);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User editRole(UserDTO userDTO) throws Exception {
        User usuario = findById(userDTO.getId());
        roleService.setRoleById(usuario, userDTO.getRoles().get(0).getId());

        return userRepository.save(usuario);
    }
}
