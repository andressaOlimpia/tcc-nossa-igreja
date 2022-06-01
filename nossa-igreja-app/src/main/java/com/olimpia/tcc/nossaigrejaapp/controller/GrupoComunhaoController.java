package com.olimpia.tcc.nossaigrejaapp.controller;

import com.olimpia.tcc.nossaigrejaapp.dto.GrupoComunhaoBasicInfoDTO;
import com.olimpia.tcc.nossaigrejaapp.dto.GrupoComunhaoDTO;
import com.olimpia.tcc.nossaigrejaapp.dto.UserInscricaoGrupoDTO;
import com.olimpia.tcc.nossaigrejaapp.model.GrupoComunhao;
import com.olimpia.tcc.nossaigrejaapp.service.GrupoComunhaoService;
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
@RequestMapping("/grupos-comunhao")
public class GrupoComunhaoController {

    private final GrupoComunhaoService grupoComunhaoService;
    private final ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<GrupoComunhaoDTO> createGrupoComunhao(@Valid @RequestBody GrupoComunhaoDTO grupoComunhaoDTO) {
        GrupoComunhao grupoComunhao = modelMapper.map(grupoComunhaoDTO, GrupoComunhao.class);
        GrupoComunhaoDTO grupoSalvo = modelMapper.map(grupoComunhaoService.save(grupoComunhao), GrupoComunhaoDTO.class);

        return new ResponseEntity<>(grupoSalvo, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBRO')")
    public ResponseEntity<List<GrupoComunhaoDTO>> getGruposComunhao(){
        List<GrupoComunhao> grupos = grupoComunhaoService.findAll();
        List<GrupoComunhaoDTO> gruposComunhaoDTO = grupos.stream()
                .map(grupo -> modelMapper.map(grupo, GrupoComunhaoDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(gruposComunhaoDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/basic")
    public ResponseEntity<List<GrupoComunhaoBasicInfoDTO>> getGruposComunhaoBasicInfo(){
        List<GrupoComunhao> grupos = grupoComunhaoService.findAll();
        List<GrupoComunhaoBasicInfoDTO> gruposComunhaoBasicDTO = grupos.stream()
                .map(grupo -> modelMapper.map(grupo, GrupoComunhaoBasicInfoDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(gruposComunhaoBasicDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBRO')")
    public ResponseEntity<GrupoComunhaoDTO> getGrupoComunhaoById(@PathVariable Long id) throws Exception {
        GrupoComunhaoDTO grupoComunhaoDTO = modelMapper.map(grupoComunhaoService.findById(id), GrupoComunhaoDTO.class);
        return new ResponseEntity<>(grupoComunhaoDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<GrupoComunhaoDTO> editGrupoComunhao(@Valid @RequestBody GrupoComunhaoDTO grupoComunhaoDTO) throws Exception {
        GrupoComunhao grupoComunhao = modelMapper.map(grupoComunhaoDTO, GrupoComunhao.class);
        GrupoComunhaoDTO grupoComunhaoSalvo = modelMapper.map(grupoComunhaoService.edit(grupoComunhao), GrupoComunhaoDTO.class);

        return new ResponseEntity<>(grupoComunhaoSalvo, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBRO')")
    public ResponseEntity<HttpStatus> deleteGrupoComunhao(@PathVariable Long id) {
        grupoComunhaoService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBRO')")
    public ResponseEntity<GrupoComunhaoDTO> inscreverParticipante(@PathVariable Long id, @RequestBody UserInscricaoGrupoDTO participante) throws Exception {
        GrupoComunhaoDTO grupoAtualizado = modelMapper
                .map(grupoComunhaoService.inscreverParticipante(id, participante.getId()), GrupoComunhaoDTO.class);
        return new ResponseEntity<>(grupoAtualizado, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/cancelar-inscricao")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBRO')")
    public ResponseEntity<GrupoComunhaoDTO> excluirParticipante(@PathVariable Long id, @RequestBody UserInscricaoGrupoDTO participante) throws Exception {
        GrupoComunhaoDTO grupoAtualizado = modelMapper
                .map(grupoComunhaoService.excluirParticipante(id, participante.getId()), GrupoComunhaoDTO.class);
        return new ResponseEntity<>(grupoAtualizado, HttpStatus.OK);
    }

    @GetMapping(value = "/participante/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBRO')")
    public ResponseEntity<List<GrupoComunhaoDTO>> getGrupoComunhaoByParticipanteId(@PathVariable Long id) {
        List<GrupoComunhaoDTO> grupoComunhaoDTO = grupoComunhaoService.encontrarGruposPorParticipanteId(id).stream()
                .map(grupo -> modelMapper.map(grupo, GrupoComunhaoDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(grupoComunhaoDTO, HttpStatus.OK);
    }
}
