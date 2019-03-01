package com.legosoft.cqrs.eventsourcing.command.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUsuarioCommand {

    @TargetAggregateIdentifier
    private String idEvent;

    private String nombreUsuario;

    private String nombreCompleto;

    private String email;

    private String password;

    private boolean administrador;

    private boolean activo;
}
