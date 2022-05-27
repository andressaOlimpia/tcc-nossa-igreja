package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.model.Role;
import com.olimpia.tcc.nossaigrejaapp.model.User;
import com.olimpia.tcc.nossaigrejaapp.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public void setRole (User usuario, String role){
        Role roleEncontrada = roleRepository.findByName(role).orElseThrow();
        usuario.setRoles(Set.of(roleEncontrada));
    }

    public void setRoleById (User usuario, Long roleId){
        Role roleEncontrada = roleRepository.findById(roleId).orElseThrow();
        usuario.getRoles().clear();
        usuario.getRoles().add(roleEncontrada);
    }

    public List<Role> findAllRoles(){
        return roleRepository.findAll();
    }
}
