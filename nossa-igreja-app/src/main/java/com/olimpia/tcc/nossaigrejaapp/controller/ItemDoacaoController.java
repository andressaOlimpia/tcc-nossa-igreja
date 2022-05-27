package com.olimpia.tcc.nossaigrejaapp.controller;

import com.olimpia.tcc.nossaigrejaapp.model.ItemDoacao;
import com.olimpia.tcc.nossaigrejaapp.service.ItemDoacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("itens-doacao")
public class ItemDoacaoController {

    private final ItemDoacaoService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ItemDoacao> createItemDoacao(@Valid @RequestBody ItemDoacao itemDoacao) {

        ItemDoacao itemSalvo = service.save(itemDoacao);
        return new ResponseEntity<>(itemSalvo, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<ItemDoacao>> getItemsByCategoriaENomePrefix(
            @RequestParam(value = "categoriaId", required=false) Long categoriaId,
            @RequestParam("prefixoNome") String prefix
    ) {
        List<ItemDoacao> itens = service.findAllFilteredByCategoriaENome(categoriaId, prefix);
        return new ResponseEntity<>(itens, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteItemDoacao(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
