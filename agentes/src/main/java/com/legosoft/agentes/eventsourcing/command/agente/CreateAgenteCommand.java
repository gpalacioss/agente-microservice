package com.legosoft.agentes.eventsourcing.command.agente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAgenteCommand {

    @TargetAggregateIdentifier
    private String idAgenteEvent;

    private String nombreAgente;

    private Date fechaCreacion;

    private boolean activo;

}
