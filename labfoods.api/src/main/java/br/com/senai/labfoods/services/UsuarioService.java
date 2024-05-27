package br.com.senai.labfoods.services;

import br.com.senai.labfoods.dtos.UsuarioDTO;
import br.com.senai.labfoods.dtos.UsuarioPublicDTO;
import br.com.senai.labfoods.models.Usuario;
import br.com.senai.labfoods.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioDTO criarUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByCpf(usuarioDTO.getCpf()).isPresent()) {
            throw new RuntimeException("CPF já cadastrado");
        }
        Usuario usuario = dtoToUsuario(usuarioDTO);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario = usuarioRepository.save(usuario);
        return usuarioToDTO(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioPublicDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::usuarioToPublicDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioPublicDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return usuarioToPublicDTO(usuario);
    }

    @Transactional
    public UsuarioDTO atualizarUsuario(Long id, UsuarioDTO usuarioDTO, UserDetails userDetails) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!usuario.getEmail().equals(userDetails.getUsername())) {
            throw new RuntimeException("Usuário não autorizado a atualizar este perfil");
        }

        updateUsuarioFromDTO(usuario, usuarioDTO);

        // Verifica se a senha foi alterada e a codifica novamente
        if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        }

        usuario = usuarioRepository.save(usuario);
        return usuarioToDTO(usuario);
    }

    @Transactional
    public void deletarUsuario(Long id, UserDetails userDetails) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!usuario.getEmail().equals(userDetails.getUsername())) {
            throw new RuntimeException("Usuário não autorizado a deletar este perfil");
        }

        if (!usuario.getReceitas().isEmpty()) {
            throw new RuntimeException("Usuário possui receitas cadastradas e não pode ser deletado");
        }

        usuarioRepository.deleteById(id);
    }

    private Usuario dtoToUsuario(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setSexo(dto.getSexo());
        usuario.setCpf(dto.getCpf());
        usuario.setEndereco(dto.getEndereco());
        usuario.setEmail(dto.getEmail());
        usuario.setDataNascimento(dto.getDataNascimento());
        usuario.setSenha(dto.getSenha());
        usuario.setRole("ROLE_USER");
        return usuario;
    }

    private UsuarioDTO usuarioToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setSexo(usuario.getSexo());
        dto.setCpf(usuario.getCpf());
        dto.setEndereco(usuario.getEndereco());
        dto.setEmail(usuario.getEmail());
        dto.setDataNascimento(usuario.getDataNascimento());
        // Não inclui a senha aqui para segurança
        return dto;
    }

    private UsuarioPublicDTO usuarioToPublicDTO(Usuario usuario) {
        UsuarioPublicDTO dto = new UsuarioPublicDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setSexo(usuario.getSexo());
        dto.setEmail(usuario.getEmail());
        return dto;
    }

    private void updateUsuarioFromDTO(Usuario usuario, UsuarioDTO dto) {
        usuario.setNome(dto.getNome());
        usuario.setSexo(dto.getSexo());
        usuario.setEndereco(dto.getEndereco());
        usuario.setEmail(dto.getEmail());
        usuario.setDataNascimento(dto.getDataNascimento());
    }
}
