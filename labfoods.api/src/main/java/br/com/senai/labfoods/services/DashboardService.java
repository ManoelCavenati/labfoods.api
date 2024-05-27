package br.com.senai.labfoods.services;

import br.com.senai.labfoods.dtos.ReceitaDTO;
import br.com.senai.labfoods.models.AvaliacaoReceita;
import br.com.senai.labfoods.models.Receita;
import br.com.senai.labfoods.repositories.UsuarioRepository;
import br.com.senai.labfoods.repositories.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ReceitaRepository receitaRepository;

    public Map<String, Object> getDashboardStats() {
        long totalUsuarios = usuarioRepository.count();
        long totalReceitas = receitaRepository.count();
        List<ReceitaDTO> topReceitas = findTopRatedReceitas();

        return Map.of(
                "totalUsuarios", totalUsuarios,
                "totalReceitas", totalReceitas,
                "topReceitas", topReceitas);
    }

    public List<ReceitaDTO> findTopRatedReceitas() {
        return receitaRepository.findAll().stream()
                .map(receita -> {
                    double mediaNota = receita.getAvaliacoes().stream()
                            .mapToDouble(AvaliacaoReceita::getNota)
                            .average()
                            .orElse(0.0);
                    return convertReceitaToDto(receita, mediaNota);
                })
                .sorted((r1, r2) -> Double.compare(r2.getMediaNota(), r1.getMediaNota()))
                .limit(3)
                .collect(Collectors.toList());
    }

    private ReceitaDTO convertReceitaToDto(Receita receita, double mediaNota) {
        ReceitaDTO dto = new ReceitaDTO();
        dto.setId(receita.getId());
        dto.setTitulo(receita.getTitulo());
        dto.setDescricao(receita.getDescricao());
        dto.setIngredientes(receita.getIngredientes());
        dto.setTempoPreparo(receita.getTempoPreparo());
        dto.setModoPreparo(receita.getModoPreparo());
        dto.setTipoReceita(receita.getTipoReceita());
        dto.setTipoDieta(receita.getTipoDieta());
        dto.setOrigem(receita.getOrigem());

        if (receita.getUsuario() != null) {
            dto.setUsuarioId(receita.getUsuario().getId());
        }

        List<Double> notas = receita.getAvaliacoes().stream()
                .map(AvaliacaoReceita::getNota)
                .collect(Collectors.toList());
        dto.setNotasRecebidas(notas);
        dto.setQuantidadeVotos(notas.size());
        dto.setMediaNota(mediaNota);

        return dto;
    }
}
