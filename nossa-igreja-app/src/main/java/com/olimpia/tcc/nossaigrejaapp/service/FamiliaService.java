package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.model.Endereco;
import com.olimpia.tcc.nossaigrejaapp.model.Familia;
import com.olimpia.tcc.nossaigrejaapp.model.PessoaSemCadastro;
import com.olimpia.tcc.nossaigrejaapp.repository.FamiliaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FamiliaService {

    private final FamiliaRepository repository;
    private final EnderecoService enderecoService;
    private final PessoaSemCadastroService pessoaSemCadastroService;

    public Familia save(Familia familia) {
        familia.setEndereco(getEnderecoSalvo(familia.getEndereco()));
        familia.setFamiliaresSemCadastro(salvarPessoasSemCadastro(familia.getFamiliaresSemCadastro()));
        familia.setCreatedDate(LocalDateTime.now());

        return repository.save(familia);
    }

    private Endereco getEnderecoSalvo(Endereco endereco){
        return endereco.getId() == null ?  enderecoService.save(endereco) : enderecoService.edit(endereco);
    }

    private List<PessoaSemCadastro> salvarPessoasSemCadastro(List<PessoaSemCadastro> pessoas){
        List<PessoaSemCadastro> pessoasSalvas = new ArrayList<>();
        pessoas.forEach(pessoa -> {
            if(pessoa.getId() == null) {
            pessoasSalvas.add(pessoaSemCadastroService.save(pessoa));}
        });
        return pessoasSalvas;
    }

    public Familia edit(Familia familia) throws Exception {
        Familia familiaAntiga = findById(familia.getId());

        familiaAntiga.setPrincipal(familia.getPrincipal());
        familiaAntiga.setFamiliares(familia.getFamiliares());
        familiaAntiga.setFamiliaresSemCadastro(
                familia.getFamiliaresSemCadastro() != null?
                        salvarPessoasSemCadastro(familia.getFamiliaresSemCadastro()) :
                        familia.getFamiliaresSemCadastro());
        familiaAntiga.setEndereco(getEnderecoSalvo(familia.getEndereco()));

        return repository.save(familia);
    }

    private void excluirPessoasSemCadastro(List<PessoaSemCadastro> pessoas){
        pessoas.forEach(pessoa -> pessoaSemCadastroService.delete(pessoa.getId()));
    }

    public Familia findById(Long id) throws Exception {return repository.findById(id)
            .orElseThrow(()-> new Exception("Familia não encontrada"));}


    public void delete(Long id){
        repository.deleteById(id);
    }

    public void deleteAll(List<Long> ids){
        repository.deleteAllById(ids);
    }

    public void deleteAll() {repository.deleteAll();}

    public List<Familia> findAll(){
        return repository.findAll();
    }

}
