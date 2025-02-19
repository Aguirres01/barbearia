package com.example.barbearia.dto;

public record ServicoDTO(
        String nome,
        double preco,
        int duracaoEmMinutos
) {
    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }
}
