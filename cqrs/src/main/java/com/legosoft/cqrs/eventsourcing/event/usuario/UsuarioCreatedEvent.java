package com.legosoft.cqrs.eventsourcing.event.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCreatedEvent {

    private String idEvent;

    private String nombreUsuario;

    private String nombreCompleto;

    private String email;

    private String password;

    private boolean administrador;

    private boolean activo;

}
