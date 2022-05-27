package com.olimpia.tcc.nossaigrejaapp.controller;

import com.olimpia.tcc.nossaigrejaapp.dto.FamiliaDTO;
import com.olimpia.tcc.nossaigrejaapp.model.Familia;
import com.olimpia.tcc.nossaigrejaapp.service.FamiliaService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/familia")
public class FamiliaController {

    private final FamiliaService service;
    private final ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<FamiliaDTO> createFamilia(@Valid @RequestBody FamiliaDTO familiaDTO) {
        Familia familia = modelMapper.map(familiaDTO, Familia.class);
        FamiliaDTO familiaSalva = modelMapper.map(service.save(familia), FamiliaDTO.class);

        return new ResponseEntity<>(familiaSalva, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<FamiliaDTO>> getFamilias(){
        List<Familia> familias = service.findAll();
        List<FamiliaDTO> familiasDTO = familias.stream()
                .map(familia -> modelMapper.map(familia, FamiliaDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(familiasDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<FamiliaDTO> getFamiliaById(@PathVariable Long id) throws Exception {
        FamiliaDTO familiaDTO = modelMapper.map(service.findById(id), FamiliaDTO.class);
        return new ResponseEntity<>(familiaDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<FamiliaDTO> editFamilia(@Valid @RequestBody FamiliaDTO familiaDTO) throws Exception {
        Familia familia = modelMapper.map(familiaDTO, Familia.class);
        FamiliaDTO familiaEditada = modelMapper.map(service.edit(familia), FamiliaDTO.class);

        return new ResponseEntity<>(familiaEditada, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteFamilia(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
