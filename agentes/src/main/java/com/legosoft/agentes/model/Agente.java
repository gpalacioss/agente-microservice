package com.legosoft.agentes.model;

import com.legosoft.agentes.eventsourcing.command.agente.CreateAgenteCommand;
import com.legosoft.agentes.eventsourcing.event.agente.AgenteCreatedEvent;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Date;

@Entity
@Aggregate
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Table(name = "agente")
public class Agente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AGENTE")
    private Long idAgente;

    @AggregateIdentifier
    @Transient
    private String idAgenteEvent;

    @Column(name = "NOMBRE_AGENTE")
    private String nombreAgente;

    @Column(name = "FECHA_CREACION")
    private Date fechaCracion;

    @Column(name = "ACTIVO")
    private boolean activo;

    @CommandHandler
    public Agente(CreateAgenteCommand createAgenteCommand) {
        log.info("Estamos en el generar Comando");
        Assert.hasLength(createAgenteCommand.getIdAgenteEvent(), "El id no debe de estar nula o vacia");
        AggregateLifecycle.apply(new AgenteCreatedEvent(createAgenteCommand.getIdAgenteEvent(), createAgenteCommand.getNombreAgente(), createAgenteCommand.getFechaCracion(), createAgenteCommand.isActivo()));
    }

    @EventSourcingHandler
    public void on(AgenteCreatedEvent agenteCreatedEvent){
        this.idAgenteEvent = agenteCreatedEvent.getIdAgenteEvent();
        this.nombreAgente = agenteCreatedEvent.getNombreAgente();
        this.fechaCracion = agenteCreatedEvent.getFechaCracion();
        this.activo = agenteCreatedEvent.isActivo();
    }
}
