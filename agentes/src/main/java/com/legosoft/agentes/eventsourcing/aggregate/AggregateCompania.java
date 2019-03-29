package com.legosoft.agentes.eventsourcing.aggregate;

import com.legosoft.agentes.eventsourcing.command.agente.*;
import com.legosoft.agentes.eventsourcing.event.agente.*;
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
        log.info("<<<<<<<<<Estamos en el generar Comando>>>>>>>>>>>>>");
        AggregateLifecycle.apply(new CompaniaCreatedEvent(createCompaniaCommand.getIdEvent(), createCompaniaCommand.getCompania()));
    }


    @EventSourcingHandler
    public void on(CompaniaCreatedEvent agenteCreatedEvent){
        this.idEvent = agenteCreatedEvent.getIdEvent();
        this.compania = agenteCreatedEvent.getCompania();
        log.info("<<<<<< Evento para Crear la comapañia >>>>>>>>>>");
    }

    @CommandHandler
    public void on(AsociarAgenteUsuarioCommand command){
        log.info("<<<<<<<<<<< comando para asociar comapañia al usuario >>>>>>>>>>>>>>");
        AggregateLifecycle.apply(new AssociatedAgenteUsuarioEvent(command.getIdEvent(), command.getCompania()));
    }

    @EventSourcingHandler
    public void on(AssociatedAgenteUsuarioEvent associatedAgenteUsuarioEvent){
        log.info("<<<<<<<<<<< Evento para asociar compañia al usuario >>>>>>>>>>>>>>");
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


    @CommandHandler
    public void on(ErrorRelationUsuarioCompaniaCommand command){
        log.info("<<<<<<<<< Comando que ejecutara el evento de error al asociar grupo con la compañia >>>>>>>>>>>>>>>>>>><");
        AggregateLifecycle.apply(new ErrorRelationUsuarioCompaniaEvent(command.getIdEvent(), command.getCompania()));
    }

    @EventSourcingHandler
    public void on(ErrorRelationUsuarioCompaniaEvent event){

        log.info("<<<<<<<<<<<<<<< Evento de  error al asociar usuario con la compañia >>>>>>>>>>>>>>>>>");
        this.idEvent = event.getIdEvent();
        this.compania = event.getCompania();

    }

    @CommandHandler
    public void on(ErrorRelationGrupoCompaniaCommand command){
        log.info("<<<<<<<<<<<<< Comando que ejecutara el evento de error al asociar grupo con la compañia >>>>>>>>>>>>>>>>>");
        AggregateLifecycle.apply(new ErrorRelationGrupoCompaniaEvent(command.getIdEvent(), command.getCompania()));
    }

    @EventSourcingHandler
    public void on(ErrorRelationGrupoCompaniaEvent event){

        log.info("<<<<<<<<<<<<<< Evento de error al relacionar la comṕañia con el grupo empresarial >>>>>>>>>><<<");
        this.idEvent = event.getIdEvent();
        this.compania = event.getCompania();

    }

}
