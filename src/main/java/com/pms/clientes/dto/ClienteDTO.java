package com.pms.clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
public record ClienteDTO(
        Integer id,
        String nombre,
        String apellidoPaterno,
        String direccion,
        String correo,
        String telefono
) {}