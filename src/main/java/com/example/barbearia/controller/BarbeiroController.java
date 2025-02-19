package com.example.barbearia.controller;

import com.example.barbearia.dto.BarbeiroDTO;
import com.example.barbearia.models.Barbeiro;
import com.example.barbearia.service.BarbeiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barbeiros")
public class BarbeiroController {

    @Autowired
    private BarbeiroService service;

    @PostMapping
    public ResponseEntity<Barbeiro> cadastrar(@RequestBody BarbeiroDTO dto) {
        return ResponseEntity.ok(service.cadastrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<Barbeiro>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Barbeiro> atualizar(@PathVariable Long id, @RequestBody BarbeiroDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

