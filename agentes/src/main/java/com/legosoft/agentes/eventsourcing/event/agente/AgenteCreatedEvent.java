package com.legosoft.agentes.eventsourcing.event.agente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgenteCreatedEvent {

    private Long idAgente;

    private String nombreAgente;

    private Date fechaCreacion;

    private boolean activo;

    private String estatus;

}
