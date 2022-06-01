package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.model.EscalaGrupoComunhao;
import com.olimpia.tcc.nossaigrejaapp.repository.EscalaGrupoComunhaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class EscalaGrupoComunhaoService {

    private final EscalaGrupoComunhaoRepository repository;

    public EscalaGrupoComunhao criarOuAlterar(EscalaGrupoComunhao escala){
        return repository.save(escala);
    }

    public void excluir(Long id){
        repository.deleteById(id);
    }

    public void deleteAll(){repository.deleteAll();}

    public List<EscalaGrupoComunhao> encontrarProximasEscalasPorGrupoParticipante (Long grupoId){
        LocalDate dataHoje = LocalDate.now();
        return repository.findByGrupoComunhaoIdAndDataGreaterThanEqualOrderByData(grupoId, dataHoje);
    }
}
