package com.legosoft.agentes.eventsourcing.aggregate;

import com.legosoft.agentes.eventsourcing.command.agente.AsociarAgenteUsuarioCommand;
import com.legosoft.agentes.eventsourcing.command.agente.AsociarCompaniaGrupoCommand;
import com.legosoft.agentes.eventsourcing.command.agente.CreateCompaniaCommand;
import com.legosoft.agentes.eventsourcing.event.agente.AssociatedAgenteUsuarioEvent;
import com.legosoft.agentes.eventsourcing.event.agente.CompaniaAssociatedGrupoEvent;
import com.legosoft.agentes.eventsourcing.event.agente.CompaniaCreatedEvent;
import com.legosoft.agentes.model.Compania;
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
import sun.rmi.runtime.Log;

import java.util.Date;

@Aggregate
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class AggregateCompania {

    @AggregateIdentifier
    private Long idEvent;

    @AggregateMember
    private Compania compania;

    @CommandHandler
    public AggregateCompania(CreateCompaniaCommand createCompaniaCommand) {
        log.info("Estamos en el generar Comando");
//        Assert.hasLength(createAgenteCommand.getIdEvent().toString(), "El id no debe de estar nula o vacia");
        AggregateLifecycle.apply(new CompaniaCreatedEvent(createCompaniaCommand.getIdEvent(), createCompaniaCommand.getCompania()));
    }


    @EventSourcingHandler
    public void on(CompaniaCreatedEvent agenteCreatedEvent){
        this.idEvent = agenteCreatedEvent.getIdEvent();
        this.compania = agenteCreatedEvent.getCompania();
        log.info("id del evento:: " + agenteCreatedEvent.getIdEvent());
    }

    @CommandHandler
    public void on(AsociarAgenteUsuarioCommand command){
        log.info("Evento para asociar agente al usuario");
        AggregateLifecycle.apply(new AssociatedAgenteUsuarioEvent(command.getIdEvent(), command.getCompania()));
    }

    @EventSourcingHandler
    public void on(AssociatedAgenteUsuarioEvent associatedAgenteUsuarioEvent){
        this.idEvent = associatedAgenteUsuarioEvent.getIdEvent();
        this.compania = associatedAgenteUsuarioEvent.getCompania();

    }

    @CommandHandler
    public void on(AsociarCompaniaGrupoCommand command){
        log.info("<<<<<<<<<<<< Comando para generar el evento de compañia asociada a un grupo empresarial >>>>>>>>>");
        AggregateLifecycle.apply(new CompaniaAssociatedGrupoEvent(command.getIdEvent(), command.getCompania()));
    }

    @EventSourcingHandler
    public void on(CompaniaAssociatedGrupoEvent event){
        log.info("<<<<<<<<<< Generando evento de asociacion de compañia a grupo >>>>>>>>>>>>>");
        this.idEvent = event.getIdEvent();
        this.compania = event.getCompania();
    }

}
