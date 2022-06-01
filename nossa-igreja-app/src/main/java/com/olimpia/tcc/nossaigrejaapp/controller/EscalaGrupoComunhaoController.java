package com.olimpia.tcc.nossaigrejaapp.controller;

import com.olimpia.tcc.nossaigrejaapp.dto.EscalaGrupoComunhaoDTO;
import com.olimpia.tcc.nossaigrejaapp.model.EscalaGrupoComunhao;
import com.olimpia.tcc.nossaigrejaapp.service.EscalaGrupoComunhaoService;
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
@RequestMapping("escalas-grupo-comunhao")
public class EscalaGrupoComunhaoController {

    private final EscalaGrupoComunhaoService service;
    private final ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBRO')")
    public ResponseEntity<EscalaGrupoComunhao> incluirEscalaParaGrupoComunhao(@Valid @RequestBody EscalaGrupoComunhao escala) {

        EscalaGrupoComunhao escalaSalva = service.criarOuAlterar(escala);
        return new ResponseEntity<>(escalaSalva, HttpStatus.CREATED);
    }

    @GetMapping("/grupo/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBRO')")
    public ResponseEntity<List<EscalaGrupoComunhaoDTO>> getProximasEscalasPorGrupo(@PathVariable Long id) {
        List<EscalaGrupoComunhao> escalas = service.encontrarProximasEscalasPorGrupoParticipante(id);
        List<EscalaGrupoComunhaoDTO> escalasDTO = escalas.stream().map(escala -> modelMapper.map(escala, EscalaGrupoComunhaoDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(escalasDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteEscalaGrupoComunhao(@PathVariable Long id) {
        service.excluir(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
