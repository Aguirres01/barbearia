package com.example.barbearia.service;

import com.example.barbearia.dto.BarbeiroDTO;
import com.example.barbearia.models.Barbeiro;
import com.example.barbearia.repository.BarbeiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarbeiroService {

    @Autowired
    private BarbeiroRepository repository;

    public Barbeiro cadastrar(BarbeiroDTO dto) {
        return repository.save(new Barbeiro(dto));
    }

    public List<Barbeiro> listar() {
        return repository.findAll();
    }

    public Barbeiro atualizar(Long id, BarbeiroDTO dto) {
        Barbeiro barbeiro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));
        barbeiro.setNome(dto.nome());
        barbeiro.setTelefone(dto.telefone());
        barbeiro.setEspecialidade(dto.especialidade());
        return repository.save(barbeiro);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}

