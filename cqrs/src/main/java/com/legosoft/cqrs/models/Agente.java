package com.legosoft.cqrs.models;

import com.legosoft.cqrs.eventsourcing.command.agente.CreateAgenteCommand;
import com.legosoft.cqrs.eventsourcing.event.agente.AgenteCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Date;

@Slf4j
@Aggregate
@NodeEntity(value = "Agente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agente implements Serializable {

    @Id
    @GeneratedValue
    @AggregateIdentifier
    private Long idAgente;

    private String nombreAgente;

    private Date fechaCracion;

    private boolean activo;

    @CommandHandler
    public Agente(CreateAgenteCommand createAgenteCommand) {
        log.info("Estamos en el generar Comando");
        Assert.hasLength(createAgenteCommand.getIdAgente().toString(), "El id no debe de estar nula o vacia");
        AggregateLifecycle.apply(new AgenteCreatedEvent(createAgenteCommand.getIdAgente(), createAgenteCommand.getNombreAgente(), createAgenteCommand.getFechaCracion(), createAgenteCommand.isActivo()));
    }

    @EventSourcingHandler
    public void on(AgenteCreatedEvent agenteCreatedEvent){
        this.idAgente = agenteCreatedEvent.getIdAgente();
        this.nombreAgente = agenteCreatedEvent.getNombreAgente();
        this.fechaCracion = agenteCreatedEvent.getFechaCracion();
        this.activo = agenteCreatedEvent.isActivo();
    }
}
