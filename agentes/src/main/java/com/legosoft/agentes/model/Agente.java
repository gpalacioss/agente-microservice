package com.legosoft.agentes.model;

import com.legosoft.agentes.eventsourcing.command.agente.AsociarAgenteUsuarioCommand;
import com.legosoft.agentes.eventsourcing.command.agente.CreateAgenteCommand;
import com.legosoft.agentes.eventsourcing.event.agente.AgenteCreatedEvent;
import com.legosoft.agentes.eventsourcing.event.agente.AssociatedAgenteUsuarioEvent;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Aggregate
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Table(name = "agente")
public class Agente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agente")
    @AggregateIdentifier
    private Long idAgente;

//    @AggregateIdentifier
//    @Transient
//    private Long idEvent;

    @Column(name = "nombre_agente")
    private String nombreAgente;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "activo")
    private boolean activo;

    @Column(name = "estatus")
    private String estatus;

    @Transient
    private String tipo = "agente";

    @CommandHandler
    public Agente(CreateAgenteCommand createAgenteCommand) {
        log.info("Estamos en el generar Comando");
//        Assert.hasLength(createAgenteCommand.getIdEvent().toString(), "El id no debe de estar nula o vacia");
        AggregateLifecycle.apply(new AgenteCreatedEvent(createAgenteCommand.getIdAgente(), createAgenteCommand.getNombreAgente(), createAgenteCommand.getFechaCreacion(), createAgenteCommand.isActivo(), createAgenteCommand.getEstatus()));
    }


    @EventSourcingHandler
    public void on(AgenteCreatedEvent agenteCreatedEvent){
        this.idAgente = agenteCreatedEvent.getIdAgente();
        this.nombreAgente = agenteCreatedEvent.getNombreAgente();
        this.fechaCreacion = agenteCreatedEvent.getFechaCreacion();
        this.activo = agenteCreatedEvent.isActivo();
        this.estatus = agenteCreatedEvent.getEstatus();
        log.info("genero el evento:: " + agenteCreatedEvent.getNombreAgente());
        log.info("genero el evento:: " + agenteCreatedEvent.getFechaCreacion());
    }

    @CommandHandler
    public void on(AsociarAgenteUsuarioCommand command){
        log.info("Evento para asociar agente al usuario");
        AggregateLifecycle.apply(new AssociatedAgenteUsuarioEvent(command.getIdEvent(), command.getNombreAgente(), new Date(), command.isActivo(), command.getEstatus()));
    }

    @EventSourcingHandler
    public void on(AssociatedAgenteUsuarioEvent associatedAgenteUsuarioEvent){
        this.idAgente = associatedAgenteUsuarioEvent.getIdAgente();
        this.nombreAgente = associatedAgenteUsuarioEvent.getNombreAgente();
        this.fechaCreacion = associatedAgenteUsuarioEvent.getFechaCreacion();
        this.activo = associatedAgenteUsuarioEvent.isActivo();
        this.estatus = associatedAgenteUsuarioEvent.getEstatus();
    }

}
