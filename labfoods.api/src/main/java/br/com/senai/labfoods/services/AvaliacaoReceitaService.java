package br.com.senai.labfoods.services;

import br.com.senai.labfoods.dtos.AvaliacaoReceitaDTO;
import br.com.senai.labfoods.exceptions.AvaliacaoPropriaReceitaException;
import br.com.senai.labfoods.exceptions.ResourceNotFoundException;
import br.com.senai.labfoods.models.AvaliacaoReceita;
import br.com.senai.labfoods.models.Receita;
import br.com.senai.labfoods.models.Usuario;
import br.com.senai.labfoods.repositories.AvaliacaoReceitaRepository;
import br.com.senai.labfoods.repositories.ReceitaRepository;
import br.com.senai.labfoods.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliacaoReceitaService {

    @Autowired
    private AvaliacaoReceitaRepository avaliacaoReceitaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Transactional
    public AvaliacaoReceitaDTO criarAvaliacao(AvaliacaoReceitaDTO avaliacaoReceitaDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Receita receita = receitaRepository.findById(avaliacaoReceitaDTO.getReceitaId())
                .orElseThrow(() -> new ResourceNotFoundException("Receita não encontrada"));

        if (receita.getUsuario().getId().equals(usuario.getId())) {
            throw new AvaliacaoPropriaReceitaException("Usuário não pode avaliar a própria receita");
        }

        AvaliacaoReceita avaliacaoReceita = new AvaliacaoReceita();
        avaliacaoReceita.setNota(avaliacaoReceitaDTO.getNota());
        avaliacaoReceita.setFeedback(avaliacaoReceitaDTO.getFeedback());
        avaliacaoReceita.setUsuario(usuario);
        avaliacaoReceita.setReceita(receita);

        avaliacaoReceita = avaliacaoReceitaRepository.save(avaliacaoReceita);
        return convertToDto(avaliacaoReceita);
    }

    @Transactional(readOnly = true)
    public List<AvaliacaoReceitaDTO> listarTodas() {
        return avaliacaoReceitaRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AvaliacaoReceitaDTO buscarPorId(Long id) {
        AvaliacaoReceita avaliacaoReceita = avaliacaoReceitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));
        return convertToDto(avaliacaoReceita);
    }

    @Transactional
    public AvaliacaoReceitaDTO atualizarAvaliacao(Long id, AvaliacaoReceitaDTO avaliacaoReceitaDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        AvaliacaoReceita avaliacaoReceita = avaliacaoReceitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        if (!avaliacaoReceita.getUsuario().getId().equals(usuario.getId())) {
            throw new AvaliacaoPropriaReceitaException("Usuários só podem atualizar suas próprias avaliações");
        }

        avaliacaoReceita.setNota(avaliacaoReceitaDTO.getNota());
        avaliacaoReceita.setFeedback(avaliacaoReceitaDTO.getFeedback());

        avaliacaoReceita = avaliacaoReceitaRepository.save(avaliacaoReceita);
        return convertToDto(avaliacaoReceita);
    }

    @Transactional
    public void deletarAvaliacao(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        AvaliacaoReceita avaliacaoReceita = avaliacaoReceitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        if (!avaliacaoReceita.getUsuario().getId().equals(usuario.getId())) {
            throw new AvaliacaoPropriaReceitaException("Usuários só podem deletar suas próprias avaliações");
        }

        avaliacaoReceitaRepository.delete(avaliacaoReceita);
    }

    private AvaliacaoReceitaDTO convertToDto(AvaliacaoReceita avaliacaoReceita) {
        AvaliacaoReceitaDTO dto = new AvaliacaoReceitaDTO();
        dto.setId(avaliacaoReceita.getId());
        dto.setNota(avaliacaoReceita.getNota());
        dto.setFeedback(avaliacaoReceita.getFeedback());
        dto.setUsuarioId(avaliacaoReceita.getUsuario().getId());
        dto.setReceitaId(avaliacaoReceita.getReceita().getId());
        return dto;
    }
}
