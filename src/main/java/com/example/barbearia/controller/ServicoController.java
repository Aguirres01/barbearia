package com.example.barbearia.controller;

import com.example.barbearia.dto.ServicoDTO;
import com.example.barbearia.models.Servico;
import com.example.barbearia.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @PostMapping
    public ResponseEntity<Servico> cadastrarServico(@RequestBody ServicoDTO dto) {
        Servico servico = servicoService.cadastrarServico(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(servico);
    }
}

