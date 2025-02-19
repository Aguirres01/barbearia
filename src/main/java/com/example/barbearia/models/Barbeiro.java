package com.example.barbearia.models;

import com.example.barbearia.dto.BarbeiroDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Configurable;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "barbeiro")
@Table(name = "barbeiros")
public class Barbeiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "nome obrigatório")
    private String nome;

    @NotBlank(message = "telefone obrigatório")
    private String telefone;

    @NotNull(message = "especialidade obrigatória")
    private Especialidade especialidade;

    public Barbeiro() {}

    public Barbeiro(BarbeiroDTO dto) {
        this.nome = dto.nome();
        this.telefone = dto.telefone();
        this.especialidade = dto.especialidade();

    }
}

