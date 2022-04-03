package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.model.Endereco;
import com.olimpia.tcc.nossaigrejaapp.model.GrupoComunhao;
import com.olimpia.tcc.nossaigrejaapp.model.User;
import com.olimpia.tcc.nossaigrejaapp.repository.GrupoComunhaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class GrupoComunhaoService {

    private final GrupoComunhaoRepository grupoComunhaoRepository;
    private final EnderecoService enderecoService;
    private final UserService usuarioService;

    public GrupoComunhao save(GrupoComunhao grupoComunhao) {
        grupoComunhao.setEndereco(getEnderecosSalvos(List.of(grupoComunhao.getEndereco())).get(0));
        grupoComunhao.setCreatedDate(LocalDateTime.now());

        return grupoComunhaoRepository.save(grupoComunhao);
    }

    private List<Endereco> getEnderecosSalvos(List<Endereco> enderecos){
        List<Endereco> enderecosGrupoComunhao = new ArrayList<>();
        enderecos.forEach(endereco -> {
            if(endereco.getId() == null){
                enderecosGrupoComunhao.add(enderecoService.save(endereco));
            }else{
                enderecosGrupoComunhao.add(enderecoService.edit(endereco));
            }
        });

        return enderecosGrupoComunhao;
    }

    public GrupoComunhao edit(GrupoComunhao grupoComunhaoNovo) throws Exception {
        GrupoComunhao grupoComunhaoAntigo = findById(grupoComunhaoNovo.getId());

        grupoComunhaoAntigo.setNome(grupoComunhaoNovo.getNome());
        grupoComunhaoAntigo.setDiaSemana(grupoComunhaoNovo.getDiaSemana());
        grupoComunhaoAntigo.setHorario(grupoComunhaoNovo.getHorario());
        grupoComunhaoAntigo.setEndereco(getEnderecosSalvos(List.of(grupoComunhaoNovo.getEndereco())).get(0));
        grupoComunhaoAntigo.setLider(grupoComunhaoNovo.getLider());
        grupoComunhaoAntigo.setParticipantes(grupoComunhaoNovo.getParticipantes());
        grupoComunhaoAntigo.setMaxParticipantes(grupoComunhaoNovo.getMaxParticipantes());

        return grupoComunhaoRepository.save(grupoComunhaoNovo);
    }

    public GrupoComunhao findById(Long id) throws Exception {return grupoComunhaoRepository.findById(id)
            .orElseThrow(()-> new Exception("Grupo não encontrado"));}


    public void delete(Long id){
        grupoComunhaoRepository.deleteById(id);
    }

    public void deleteAll(List<Long> ids){
        grupoComunhaoRepository.deleteAllById(ids);
    }

    public List<GrupoComunhao> findAll(){
        return grupoComunhaoRepository.findAll();
    }

    public GrupoComunhao inscreverParticipante(Long idGrupo, Long idUsuario) throws Exception{
        User novoParticipante = usuarioService.findById(idUsuario);
        GrupoComunhao grupoComunhao = this.findById(idGrupo);

        if(grupoComunhao.getParticipantes() == null){
            List<User> participantes = new ArrayList<>();
            participantes.add(novoParticipante);

            grupoComunhao.setParticipantes(participantes);
        }
        grupoComunhao.getParticipantes().add(novoParticipante);

        return grupoComunhaoRepository.save(grupoComunhao);
    }
}
