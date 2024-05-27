package br.com.senai.labfoods.controllers;

import br.com.senai.labfoods.dtos.ReceitaDTO;
import br.com.senai.labfoods.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/receitas")
    public ResponseEntity<List<ReceitaDTO>> listarReceitasComMediaNotas() {
        List<ReceitaDTO> receitas = dashboardService.findTopRatedReceitas();
        return ResponseEntity.ok(receitas);
    }
}
