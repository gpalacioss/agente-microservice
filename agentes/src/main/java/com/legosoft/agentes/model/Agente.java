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
public class Agente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agente")
    private Long idAgente;

    @Transient
    @AggregateIdentifier
    private Long idEvent;

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

}
