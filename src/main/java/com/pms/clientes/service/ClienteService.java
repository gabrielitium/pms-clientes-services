package com.pms.clientes.service;

import com.pms.clientes.dto.ClienteDTO;
import com.pms.clientes.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {

    List<Cliente> getAllClientes();

    Optional<Cliente> getClienteById(Integer id);

    Cliente createCliente(ClienteDTO clienteDTO);

    Cliente updateCliente(Integer id, ClienteDTO clienteDTO);

    void deleteCliente(Integer id);
}
