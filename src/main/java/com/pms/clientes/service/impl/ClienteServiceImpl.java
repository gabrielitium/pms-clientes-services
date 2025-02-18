package com.pms.clientes.service.impl;

import com.pms.clientes.dto.ClienteDTO;
import com.pms.clientes.model.Cliente;
import com.pms.clientes.repository.ClienteRepository;
import com.pms.clientes.service.ClienteService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> getClienteById(Integer id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public Cliente createCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente(
                clienteDTO.nombre(),
                clienteDTO.apellidoPaterno(),
                clienteDTO.direccion(),
                clienteDTO.correo(),
                clienteDTO.telefono()
        );
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente updateCliente(Integer id, ClienteDTO clienteDTO) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombre(clienteDTO.nombre());
                    cliente.setApellidoPaterno(clienteDTO.apellidoPaterno());
                    cliente.setDireccion(clienteDTO.direccion());
                    cliente.setCorreo(clienteDTO.correo());
                    cliente.setTelefono(clienteDTO.telefono());
                    cliente.setFechaActualizacion(LocalDateTime.now());
                    return clienteRepository.save(cliente);
                }).orElseThrow(() -> new RuntimeException("Cliente not found"));
    }

    public void deleteCliente(Integer id) {
        clienteRepository.deleteById(id);
    }
}