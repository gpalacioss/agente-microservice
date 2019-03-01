package com.legosoft.cqrs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UsuarioDto implements Serializable {

    private Long id;

    private String idEvent;

    private String nombreUsuario;

    private String nombreCompleto;

    private String email;

    private String password;

    private boolean administrador;

    private boolean activo;
}
