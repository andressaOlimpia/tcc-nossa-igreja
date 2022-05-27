package com.olimpia.tcc.nossaigrejaapp.controller;

import com.olimpia.tcc.nossaigrejaapp.dto.CategoriaDoacaoQuantidadeDTO;
import com.olimpia.tcc.nossaigrejaapp.dto.DoacaoBasicInfoDTO;
import com.olimpia.tcc.nossaigrejaapp.dto.DoacaoDTO;
import com.olimpia.tcc.nossaigrejaapp.dto.DoacoesQuantidadePorMesDTO;
import com.olimpia.tcc.nossaigrejaapp.model.Doacao;
import com.olimpia.tcc.nossaigrejaapp.service.DoacaoService;
import com.olimpia.tcc.nossaigrejaapp.service.EstatisticasDoacaoService;
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
@RequestMapping("doacoes")
public class DoacaoController {

    private final DoacaoService service;
    private final EstatisticasDoacaoService estatisticasDoacaoService;
    private final ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<DoacaoDTO> createDoacao(@Valid @RequestBody DoacaoDTO doacaoDTO) {
        Doacao doacao = modelMapper.map(doacaoDTO, Doacao.class);
        DoacaoDTO doacaoSalva = modelMapper.map(service.save(doacao), DoacaoDTO.class);

        return new ResponseEntity<>(doacaoSalva, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<DoacaoDTO>> getDoacoes(){
        List<Doacao> doacoes = service.findAll();
        List<DoacaoDTO> doacoesDTO = doacoes.stream()
                .map(doacao -> modelMapper.map(doacao,DoacaoDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(doacoesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/basic")
    public ResponseEntity<List<DoacaoBasicInfoDTO>> getDoacoesBasicInfo(){
        List<Doacao> doacoes = service.findAllDisponivelDoacao();
        List<DoacaoBasicInfoDTO> doacoesBasicDTO = doacoes.stream()
                .map(doacao -> modelMapper.map(doacao, DoacaoBasicInfoDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(doacoesBasicDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/doar")
    public ResponseEntity<List<DoacaoBasicInfoDTO>> reservarDoacao(@RequestBody DoacaoDTO doacaoDTO) throws Exception{
        Doacao doacao = modelMapper.map(doacaoDTO, Doacao.class);
        List<DoacaoBasicInfoDTO> doacoesBasicDTO = service.doar(doacao).stream()
                .map(doacaoReserva -> modelMapper.map(doacaoReserva, DoacaoBasicInfoDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(doacoesBasicDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<DoacaoDTO> getDoacaoById(@PathVariable Long id) throws Exception {
        DoacaoDTO doacaoDTO = modelMapper.map(service.findById(id), DoacaoDTO.class);
        return new ResponseEntity<>(doacaoDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<DoacaoDTO> editDoacao(@Valid @RequestBody DoacaoDTO doacaoDTO) {
        Doacao doacao = modelMapper.map(doacaoDTO, Doacao.class);
        DoacaoDTO grupoComunhaoSalvo = modelMapper.map(service.edit(doacao), DoacaoDTO.class);

        return new ResponseEntity<>(grupoComunhaoSalvo, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteDoacao(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<DoacaoDTO> alterarStatusRecebimentoDoacao(@PathVariable Long id, @RequestBody DoacaoDTO doacaoDTO) throws Exception {
        DoacaoDTO doacaoRecebida = modelMapper.map(service.alterarStatusRecebimento(id), DoacaoDTO.class);
        return new ResponseEntity<>(doacaoRecebida,HttpStatus.OK);
    }

    @GetMapping(value = "/estatisticas/principais-categorias")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<CategoriaDoacaoQuantidadeDTO>> getCincoPrincipaisCategoriasDoacao() {
        return new ResponseEntity<>(estatisticasDoacaoService.quantificarCincoPrincipaisCategoriasDoacao(), HttpStatus.OK);
    }

    @GetMapping(value = "/estatisticas/doacoes-mensais")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<DoacoesQuantidadePorMesDTO>> getQuantidadeDoacoesUltimosSeisMeses() {
        return new ResponseEntity<>(estatisticasDoacaoService.quantificarDoacoesUltimosSeisMes(), HttpStatus.OK);
    }
}
