package com.example.barbearia.models;

import com.example.barbearia.dto.ClienteDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor
@Entity(name = "cliente")
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "nome obrigatório")
    private String nome;

    @NotBlank(message = "telefone  obrigatório")
    private String telefone;

    private String email;

    public Cliente() {}

    public Cliente(ClienteDTO dto) {
        this.nome = dto.nome();
        this.telefone = dto.telefone();
        this.email = dto.email();
    }
}

