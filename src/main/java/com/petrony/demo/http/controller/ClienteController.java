package com.petrony.demo.http.controller;

import com.petrony.demo.entity.Cliente;
import com.petrony.demo.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvar(@RequestBody Cliente cliente) {
        return clienteService.salvar(cliente);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> listaClientes() {
        return this.clienteService.listClientes();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente buscaClientePorId(@PathVariable("id") Long id) {
        return this.clienteService.buscaPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCliente(@PathVariable("id") Long id) {
        this.clienteService.buscaPorId(id).map(cliente -> {
            this.clienteService.removeCliente(cliente.getId());
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    /**
     * Como existe um Bean do ModelMapper na classe DemoApplication
     * desativado a substituição de valores null, caso venha algum atributo
     * com valor null, esta informação não sera aplicada ao cliente da base.
     * PERMANECENDO O VALOR QUE JA ESTA SALVO NA BASE.
     */
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarCliente(@PathVariable("id") Long id, @RequestBody Cliente cliente) {
        this.clienteService.buscaPorId(id).map(clienteBase -> {
            this.modelMapper.map(cliente, clienteBase);
            this.clienteService.salvar(clienteBase);
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }
}
