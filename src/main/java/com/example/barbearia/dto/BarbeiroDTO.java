package com.example.barbearia.dto;


import com.example.barbearia.models.Especialidade;
import jakarta.validation.constraints.NotBlank;

public record BarbeiroDTO(
        String nome,
        String telefone,

        @NotBlank(message = "especialidade obrigatória")
        Especialidade especialidade
) {}
