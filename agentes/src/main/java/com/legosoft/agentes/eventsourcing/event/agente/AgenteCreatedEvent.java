package com.legosoft.agentes.eventsourcing.event.agente;

import com.legosoft.agentes.model.Agente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgenteCreatedEvent {

    private Long idEvent;

    private Agente agente;

}
