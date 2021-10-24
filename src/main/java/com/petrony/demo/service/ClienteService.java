package com.petrony.demo.service;

import com.petrony.demo.entity.Cliente;
import com.petrony.demo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }

    public List<Cliente> listClientes() {
        return this.clienteRepository.findAll();
    }

    public Optional<Cliente> buscaPorId(Long id) {
        return this.clienteRepository.findById(id);
    }

    public void removeCliente(Long id) {
        this.clienteRepository.deleteById(id);
    }
}

