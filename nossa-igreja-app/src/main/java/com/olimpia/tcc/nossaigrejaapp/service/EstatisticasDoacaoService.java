package com.olimpia.tcc.nossaigrejaapp.service;

import com.olimpia.tcc.nossaigrejaapp.dto.CategoriaDoacaoQuantidadeDTO;
import com.olimpia.tcc.nossaigrejaapp.dto.DoacoesQuantidadePorMesDTO;
import com.olimpia.tcc.nossaigrejaapp.model.CategoriaDoacao;
import com.olimpia.tcc.nossaigrejaapp.model.Doacao;
import com.olimpia.tcc.nossaigrejaapp.model.MesesEnum;
import com.olimpia.tcc.nossaigrejaapp.repository.DoacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
@Service
public class EstatisticasDoacaoService {

    private final DoacaoRepository doacaoRepository;

    public List<CategoriaDoacaoQuantidadeDTO> quantificarCincoPrincipaisCategoriasDoacao(){
        List<CategoriaDoacaoQuantidadeDTO> quantidadesPorCategoriaList = new ArrayList<>();
        List<Doacao> doacoes = doacaoRepository.findAll();

        List<CategoriaDoacao> categoriasDoacoes = doacoes.stream()
                .map((doacao -> doacao.getItem().getCategoria())).distinct().collect(Collectors.toList());

        categoriasDoacoes.forEach(categoria -> {
            CategoriaDoacaoQuantidadeDTO categoriaDoacaoQuantidadeDTO = new CategoriaDoacaoQuantidadeDTO();
            categoriaDoacaoQuantidadeDTO.setCategoria(categoria.getNome());

            long quantidade = doacoes.stream()
                    .filter((doacao -> doacao.getItem().getCategoria().getId().equals(categoria.getId()))).count();

            categoriaDoacaoQuantidadeDTO.setQuantidade(BigInteger.valueOf(quantidade));
            quantidadesPorCategoriaList.add(categoriaDoacaoQuantidadeDTO);
        });

        quantidadesPorCategoriaList.sort(Comparator.comparing(CategoriaDoacaoQuantidadeDTO::getQuantidade).reversed());

        int toIndex = Math.min(quantidadesPorCategoriaList.size(), 5);

        return quantidadesPorCategoriaList.subList(0,toIndex);
    }

    public List<DoacoesQuantidadePorMesDTO> quantificarDoacoesUltimosSeisMes(){
        List<DoacoesQuantidadePorMesDTO> doacoesQuantidadePorMesList = new ArrayList<>();

        LocalDate dataAtual = LocalDate.now();
        LocalDateTime dataCorte = dataAtual.minusMonths(5).atStartOfDay().withDayOfMonth(1);
        List<Doacao> doacoes = doacaoRepository.findByCreatedDateGreaterThanEqual(dataCorte);

        IntStream.range(0,6).forEach( number -> {
            int mesDeReferencia = dataAtual.minusMonths(number).getMonth().getValue();

            MesesEnum mesEncontrado = Arrays.stream(MesesEnum.values()).filter(mes -> mes.ordinal()+1 == mesDeReferencia)
                    .findFirst().orElseThrow();

            long quantidadeDoacoes = doacoes.stream()
                    .filter(doacao -> doacao.getCreatedDate().getMonth().getValue() == mesDeReferencia).count();

            DoacoesQuantidadePorMesDTO dto = new DoacoesQuantidadePorMesDTO();
            dto.setMes(mesEncontrado.getDescricao());
            dto.setQuantidade(BigInteger.valueOf(quantidadeDoacoes));
            doacoesQuantidadePorMesList.add(dto);
        });

        return doacoesQuantidadePorMesList;
    }
}
