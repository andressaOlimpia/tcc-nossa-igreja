package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.model.Role;
import com.olimpia.tcc.nossaigrejaapp.model.User;
import com.olimpia.tcc.nossaigrejaapp.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public void setRole (User usuario, String role){
        Role roleVisitante = roleRepository.findByName(role).orElseThrow();
        usuario.setRoles(Set.of(roleVisitante));
    }
}
