package com.legosoft.agentes.eventsourcing.command.agente;

import com.legosoft.agentes.model.Agente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAgenteCommand {

    @TargetAggregateIdentifier
    private Long idEvent;

    private Agente agente;

}
