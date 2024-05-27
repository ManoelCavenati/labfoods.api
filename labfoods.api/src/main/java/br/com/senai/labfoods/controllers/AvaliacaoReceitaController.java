package br.com.senai.labfoods.controllers;

import br.com.senai.labfoods.dtos.AvaliacaoReceitaDTO;
import br.com.senai.labfoods.services.AvaliacaoReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoReceitaController {

    @Autowired
    private AvaliacaoReceitaService avaliacaoReceitaService;

    @PostMapping
    public ResponseEntity<AvaliacaoReceitaDTO> criarAvaliacao(
            @Valid @RequestBody AvaliacaoReceitaDTO avaliacaoReceitaDTO) {
        AvaliacaoReceitaDTO novaAvaliacao = avaliacaoReceitaService.criarAvaliacao(avaliacaoReceitaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAvaliacao);
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoReceitaDTO>> listarTodas() {
        List<AvaliacaoReceitaDTO> avaliacoes = avaliacaoReceitaService.listarTodas();
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoReceitaDTO> buscarPorId(@PathVariable Long id) {
        AvaliacaoReceitaDTO avaliacao = avaliacaoReceitaService.buscarPorId(id);
        return ResponseEntity.ok(avaliacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoReceitaDTO> atualizarAvaliacao(@PathVariable Long id,
            @Valid @RequestBody AvaliacaoReceitaDTO avaliacaoReceitaDTO) {
        AvaliacaoReceitaDTO avaliacaoAtualizada = avaliacaoReceitaService.atualizarAvaliacao(id, avaliacaoReceitaDTO);
        return ResponseEntity.ok(avaliacaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAvaliacao(@PathVariable Long id) {
        avaliacaoReceitaService.deletarAvaliacao(id);
        return ResponseEntity.noContent().build();
    }
}
