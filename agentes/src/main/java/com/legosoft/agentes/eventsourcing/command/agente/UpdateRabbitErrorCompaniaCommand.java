package com.legosoft.agentes.eventsourcing.command.agente;

import com.legosoft.agentes.model.Compania;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRabbitErrorCompaniaCommand {

    @TargetAggregateIdentifier
    private Long idEvent;

    private Compania compania;
}
