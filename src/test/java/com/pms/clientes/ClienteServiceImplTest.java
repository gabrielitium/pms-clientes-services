package com.pms.clientes;

import com.pms.clientes.dto.ClienteDTO;
import com.pms.clientes.exception.ClienteNotFoundException;
import com.pms.clientes.model.Cliente;
import com.pms.clientes.repository.ClienteRepository;
import com.pms.clientes.service.impl.ClienteServiceImpl;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Juan", "Perez", "Av. Reforma 123", "juan.perez@example.com", "5551234567");
        cliente.setId(1);
        cliente.setFechaCreacion(LocalDateTime.now());
        cliente.setFechaActualizacion(LocalDateTime.now());

        clienteDTO = new ClienteDTO(1,"Juan", "Perez", "Av. Reforma 123", "juan.perez@example.com", "5551234567");
    }

    @Test
    void shouldReturnAllClientes() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<Cliente> clientes = clienteService.getAllClientes();

        assertThat(clientes).hasSize(1);
        assertThat(clientes.get(0).getId()).isEqualTo(cliente.getId());

        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnClienteById() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        Optional<Cliente> foundCliente = clienteService.getClienteById(1);

        assertThat(foundCliente).isPresent();
        assertThat(foundCliente.get().getId()).isEqualTo(cliente.getId());

        verify(clienteRepository, times(1)).findById(1);
    }

    @Test
    void shouldReturnEmptyWhenClienteNotFound() {
        when(clienteRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Cliente> foundCliente = clienteService.getClienteById(99);

        assertThat(foundCliente).isEmpty();

        verify(clienteRepository, times(1)).findById(99);
    }

    @Test
    void shouldCreateClienteSuccessfully() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente savedCliente = clienteService.createCliente(clienteDTO);

        assertThat(savedCliente).isNotNull();
        assertThat(savedCliente.getNombre()).isEqualTo(clienteDTO.nombre());
        assertThat(savedCliente.getCorreo()).isEqualTo(clienteDTO.correo());

        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void shouldUpdateClienteSuccessfully() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente updatedCliente = clienteService.updateCliente(1, clienteDTO);

        assertThat(updatedCliente).isNotNull();
        assertThat(updatedCliente.getNombre()).isEqualTo(clienteDTO.nombre());
        assertThat(updatedCliente.getApellidoPaterno()).isEqualTo(clienteDTO.apellidoPaterno());
        assertThat(updatedCliente.getCorreo()).isEqualTo(clienteDTO.correo());

        verify(clienteRepository, times(1)).findById(1);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void shouldDeleteClienteSuccessfully() {
        when(clienteRepository.existsById(1)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(1);
        clienteService.deleteCliente(1);

        AssertionsForClassTypes.assertThat(clienteRepository.existsById(1)).isNotNull();
        verify(clienteRepository, times(1)).existsById(1);
        verify(clienteRepository, times(1)).deleteById(1);
    }

}