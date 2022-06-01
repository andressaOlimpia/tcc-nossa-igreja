package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.model.Doacao;
import com.olimpia.tcc.nossaigrejaapp.repository.DoacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class DoacaoService {

    private final DoacaoRepository repository;

    public Doacao save(Doacao doacao){
        doacao.setCreatedDate(LocalDateTime.now());
        return repository.save(doacao);
    }

    public Doacao edit(Doacao doacao){
        return repository.save(doacao);
    }

    public List<Doacao> doar(Doacao doacao) throws Exception {
        List<Doacao> doacoes = new ArrayList<>();

        Doacao doacaoSelecionada = findById(doacao.getId());

        if(!doacao.getQuantidadeReservada().equals(doacaoSelecionada.getQuantidade())){
            Doacao novaDoacao = new Doacao();
            novaDoacao.setItem(doacaoSelecionada.getItem());
            novaDoacao.setTamanho(doacaoSelecionada.getTamanho());
            novaDoacao.setQuantidade(doacaoSelecionada.getQuantidade().subtract(doacao.getQuantidadeReservada()));
            novaDoacao.setFamilia(doacaoSelecionada.getFamilia());
            novaDoacao.setDetalhes(doacaoSelecionada.getDetalhes());
            novaDoacao.setCreatedDate(doacaoSelecionada.getCreatedDate());
            doacoes.add(repository.save(novaDoacao));
        }
        doacaoSelecionada.setDoador(doacao.getDoador());
        doacaoSelecionada.setDataEntrega(doacao.getDataEntrega());
        doacaoSelecionada.setQuantidadeReservada(doacao.getQuantidadeReservada());
        doacaoSelecionada.setDataReserva(LocalDate.now());
        doacaoSelecionada.setQuantidade(doacao.getQuantidadeReservada());

        doacoes.add(edit(doacaoSelecionada));

        return doacoes;
    }

    public Doacao findById(Long id) throws Exception {return repository.findById(id)
            .orElseThrow(()-> new Exception("Doacao não encontrada"));}

    public List<Doacao> findAll(){
        return repository.findAll();
    }

    public List<Doacao> findAllDisponivelDoacao(){
        return repository.findByDataEntregaIsNull();
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public Doacao alterarStatusRecebimento(Long id) throws Exception{
        Doacao doacaoRecebida = findById(id);
        doacaoRecebida.setEntregaRealizada(!doacaoRecebida.isEntregaRealizada());
        return edit(doacaoRecebida);
    }

    public void deleteAll() {repository.deleteAll();}
}
