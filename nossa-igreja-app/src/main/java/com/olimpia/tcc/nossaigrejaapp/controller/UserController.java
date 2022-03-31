package com.olimpia.tcc.nossaigrejaapp.controller;

import com.olimpia.tcc.nossaigrejaapp.dto.UserCadastroDTO;
import com.olimpia.tcc.nossaigrejaapp.dto.UserDTO;
import com.olimpia.tcc.nossaigrejaapp.model.User;
import com.olimpia.tcc.nossaigrejaapp.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;


    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCadastroDTO userCadastroDTO) throws Exception {
        User usuario = modelMapper.map(userCadastroDTO, User.class);
        UserDTO usuarioSalvo = modelMapper.map(userService.save(usuario), UserDTO.class);

        return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        UserDTO usuario = modelMapper.map(userService.findById(id), UserDTO.class);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsersByFirstNamePrefix(@RequestParam("prefix") String prefix){
        List<User> usuarios = userService.findAllFilteredByFirstName(prefix);
        List<UserDTO> usuariosDTO = usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UserDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(usuariosDTO, HttpStatus.OK);
    }
}
