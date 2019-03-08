package com.legosoft.agentes.eventsourcing.command.agente;

import lombok.*;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AsociarAgenteUsuarioCommand {

    @TargetAggregateIdentifier
    private Long idEvent;

    private String nombreAgente;

    private Date fechaCreacion;

    private boolean activo;

    private String estatus;

}
