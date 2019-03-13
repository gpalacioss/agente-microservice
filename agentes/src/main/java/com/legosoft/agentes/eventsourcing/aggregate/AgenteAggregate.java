package com.legosoft.agentes.eventsourcing.aggregate;

import com.legosoft.agentes.eventsourcing.command.agente.AsociarAgenteUsuarioCommand;
import com.legosoft.agentes.eventsourcing.command.agente.CreateAgenteCommand;
import com.legosoft.agentes.eventsourcing.event.agente.AgenteCreatedEvent;
import com.legosoft.agentes.eventsourcing.event.agente.AssociatedAgenteUsuarioEvent;
import com.legosoft.agentes.model.Agente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.*;
import java.util.Date;

@Aggregate
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class AgenteAggregate {

    @AggregateIdentifier
    private Long idEvent;

    @AggregateMember
    private Agente agente;

    @CommandHandler
    public AgenteAggregate(CreateAgenteCommand createAgenteCommand) {
        log.info("Estamos en el generar Comando");
//        Assert.hasLength(createAgenteCommand.getIdEvent().toString(), "El id no debe de estar nula o vacia");
        AggregateLifecycle.apply(new AgenteCreatedEvent(createAgenteCommand.getIdEvent(), createAgenteCommand.getAgente()));
    }


    @EventSourcingHandler
    public void on(AgenteCreatedEvent agenteCreatedEvent){
        this.idEvent = agenteCreatedEvent.getIdEvent();
        this.agente = agenteCreatedEvent.getAgente();
        log.info("id del evento:: " + agenteCreatedEvent.getIdEvent());
    }

    @CommandHandler
    public void on(AsociarAgenteUsuarioCommand command){
        log.info("Evento para asociar agente al usuario");
        AggregateLifecycle.apply(new AssociatedAgenteUsuarioEvent(command.getIdEvent(), command.getNombreAgente(), new Date(), command.isActivo(), command.getEstatus()));
    }

    @EventSourcingHandler
    public void on(AssociatedAgenteUsuarioEvent associatedAgenteUsuarioEvent){

    }

}
