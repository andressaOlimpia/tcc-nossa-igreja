package com.olimpia.tcc.nossaigrejaapp.controller;

import com.olimpia.tcc.nossaigrejaapp.model.CategoriaDoacao;
import com.olimpia.tcc.nossaigrejaapp.service.CategoriaDoacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("categorias-doacao")
public class CategoriaDoacaoController {

    private final CategoriaDoacaoService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<CategoriaDoacao> createCategoriaDoacao(@Valid @RequestBody CategoriaDoacao categoriaDoacao) {

        CategoriaDoacao categoriaSalva = service.save(categoriaDoacao);
        return new ResponseEntity<>(categoriaSalva, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<CategoriaDoacao>> getCategoriasByNomePrefix(@RequestParam("prefix") String prefix){
        List<CategoriaDoacao> categorias = service.findAllFilteredByNome(prefix);

        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteCategoriaDoacao(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
