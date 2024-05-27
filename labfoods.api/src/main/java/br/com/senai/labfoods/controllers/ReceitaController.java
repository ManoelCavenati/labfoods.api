package br.com.senai.labfoods.controllers;

import br.com.senai.labfoods.dtos.ReceitaDTO;
import br.com.senai.labfoods.services.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @PostMapping
    public ResponseEntity<ReceitaDTO> criarReceita(@Valid @RequestBody ReceitaDTO receitaDTO) {
        ReceitaDTO novaReceita = receitaService.criarReceita(receitaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaReceita);
    }

    @GetMapping
    public ResponseEntity<List<ReceitaDTO>> listarTodas() {
        List<ReceitaDTO> receitas = receitaService.listarTodas();
        return ResponseEntity.ok(receitas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceitaDTO> buscarPorId(@PathVariable Long id) {
        ReceitaDTO receita = receitaService.buscarPorId(id);
        return ResponseEntity.ok(receita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceitaDTO> atualizarReceita(@PathVariable Long id,
            @Valid @RequestBody ReceitaDTO receitaDTO) {
        ReceitaDTO receitaAtualizada = receitaService.atualizarReceita(id, receitaDTO);
        return ResponseEntity.ok(receitaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarReceita(@PathVariable Long id) {
        receitaService.deletarReceita(id);
        return ResponseEntity.noContent().build();
    }
}
