package com.example.barbearia.service;

import com.example.barbearia.dto.ClienteDTO;
import com.example.barbearia.models.Cliente;
import com.example.barbearia.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente cadastrar(ClienteDTO dto) {
        return repository.save(new Cliente(dto));
    }

    public List<Cliente> listar() {
        return repository.findAll();
    }

    public Cliente atualizar(Long id, ClienteDTO dto) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        cliente.setNome(dto.nome());
        cliente.setTelefone(dto.telefone());
        cliente.setEmail(dto.email());
        return repository.save(cliente);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}

