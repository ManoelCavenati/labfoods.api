package br.com.senai.labfoods.services;

import br.com.senai.labfoods.dtos.ReceitaDTO;
import br.com.senai.labfoods.models.Receita;
import br.com.senai.labfoods.models.Usuario;
import br.com.senai.labfoods.repositories.ReceitaRepository;
import br.com.senai.labfoods.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ReceitaDTO criarReceita(ReceitaDTO receitaDTO) {
        Receita receita = convertDtoToReceita(receitaDTO);
        Receita novaReceita = receitaRepository.save(receita);
        return convertReceitaToDto(novaReceita);
    }

    public List<ReceitaDTO> listarTodas() {
        return receitaRepository.findAll().stream()
                .map(this::convertReceitaToDto)
                .collect(Collectors.toList());
    }

    public ReceitaDTO buscarPorId(Long id) {
        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
        return convertReceitaToDto(receita);
    }

    public ReceitaDTO atualizarReceita(Long id, ReceitaDTO receitaDTO) {
        Receita receitaExistente = receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
        receitaExistente.setTitulo(receitaDTO.getTitulo());
        receitaExistente.setDescricao(receitaDTO.getDescricao());
        receitaExistente.setIngredientes(receitaDTO.getIngredientes());
        receitaExistente.setTempoPreparo(receitaDTO.getTempoPreparo());
        receitaExistente.setModoPreparo(receitaDTO.getModoPreparo());
        receitaExistente.setTipoReceita(receitaDTO.getTipoReceita());
        receitaExistente.setTipoDieta(receitaDTO.getTipoDieta());
        receitaExistente.setOrigem(receitaDTO.getOrigem());
        Receita receitaAtualizada = receitaRepository.save(receitaExistente);
        return convertReceitaToDto(receitaAtualizada);
    }

    public void deletarReceita(Long id) {
        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
        receitaRepository.delete(receita);
    }

    private Receita convertDtoToReceita(ReceitaDTO receitaDTO) {
        Receita receita = new Receita();
        receita.setTitulo(receitaDTO.getTitulo());
        receita.setDescricao(receitaDTO.getDescricao());
        receita.setIngredientes(receitaDTO.getIngredientes());
        receita.setTempoPreparo(receitaDTO.getTempoPreparo());
        receita.setModoPreparo(receitaDTO.getModoPreparo());
        receita.setTipoReceita(receitaDTO.getTipoReceita());
        receita.setTipoDieta(receitaDTO.getTipoDieta());
        receita.setOrigem(receitaDTO.getOrigem());

        if (receitaDTO.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(receitaDTO.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            receita.setUsuario(usuario);
        }

        return receita;
    }

    private ReceitaDTO convertReceitaToDto(Receita receita) {
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

        return dto;
    }
}
