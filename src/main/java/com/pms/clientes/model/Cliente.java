package com.pms.clientes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 60)
    private String nombre;

    @Column(name = "apellido_paterno", nullable = false, length = 60)
    private String apellidoPaterno;

    @Column(name = "direccion", length = 100)
    private String direccion;

    @Column(name = "correo", nullable = false, unique = true, length = 80)
    private String correo;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

    public Cliente(String nombre, String apellidoPaterno, String direccion, String correo, String telefono) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.direccion = direccion;
        this.correo = correo;
        this.telefono = telefono;
    }

}