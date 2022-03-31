package com.olimpia.tcc.nossaigrejaapp.controller;

import com.olimpia.tcc.nossaigrejaapp.dto.GrupoComunhaoDTO;
import com.olimpia.tcc.nossaigrejaapp.model.GrupoComunhao;
import com.olimpia.tcc.nossaigrejaapp.service.GrupoComunhaoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/grupos-comunhao")
public class GrupoComunhaoController {

    private final GrupoComunhaoService grupoComunhaoService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<GrupoComunhaoDTO> createGrupoComunhao(@Valid @RequestBody GrupoComunhaoDTO grupoComunhaoDTO) {
        GrupoComunhao grupoComunhao = modelMapper.map(grupoComunhaoDTO, GrupoComunhao.class);
        GrupoComunhaoDTO grupoSalvo = modelMapper.map(grupoComunhaoService.save(grupoComunhao), GrupoComunhaoDTO.class);

        return new ResponseEntity<>(grupoSalvo, HttpStatus.CREATED);
    }
}
