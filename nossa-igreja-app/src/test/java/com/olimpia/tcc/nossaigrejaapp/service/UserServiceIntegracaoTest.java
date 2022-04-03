package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.dto.UserDTO;
import com.olimpia.tcc.nossaigrejaapp.model.Role;
import com.olimpia.tcc.nossaigrejaapp.model.User;
import com.olimpia.tcc.nossaigrejaapp.repository.RoleRepository;
import com.olimpia.tcc.nossaigrejaapp.testhelpers.UsuarioTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

@ActiveProfiles("test")
@SpringBootTest
class UserServiceIntegracaoTest {

    public static final String URL_CADASTRO = "/users/cadastrar";

    @Autowired
    private UserService service;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    private UserDTO userDTO;

    @BeforeEach
    public void setup(){
        userDTO = UsuarioTestHelper.createUserDTO();

        Role roleVisitante = new Role();
        roleVisitante.setName("ROLE_VISITANTE");
        roleRepository.save(roleVisitante);
    }

    @Test
    @Transactional
    void deveCadastrarNovoUsuario() throws Exception {
        User usuarioParaCadastrar  = modelMapper.map(userDTO, User.class);
        usuarioParaCadastrar.setPassword("senhaSuperSecreta");
        User usuarioCadastrado = service.save(usuarioParaCadastrar);

        Assertions.assertEquals(usuarioParaCadastrar.getFirstName(), usuarioCadastrado.getFirstName());
        Assertions.assertEquals(usuarioParaCadastrar.getLastName(), usuarioCadastrado.getLastName());
        Assertions.assertEquals(usuarioParaCadastrar.getBirthDate(), usuarioCadastrado.getBirthDate());
        Assertions.assertEquals(usuarioParaCadastrar.getEmail(), usuarioCadastrado.getEmail());
    }

    @Test
    @Transactional
    void naoDeveCadastrarUsuarioSeJaExistirEmailCadastrado() throws Exception {
        User usuario1 = modelMapper.map(userDTO, User.class);
        usuario1.setEmail("email@igual.com");
        usuario1.setPassword("senha");
        service.save(usuario1);

        User outroUsuario = modelMapper.map(userDTO, User.class);
        outroUsuario.setEmail("email@igual.com");
        outroUsuario.setPassword("senhaSuperSecreta");

        Assertions.assertThrows(Exception.class,() -> service.save(outroUsuario));
    }
}
