package com.legosoft.cqrs.models;

import com.legosoft.cqrs.enums.GrupoEmpresarialEstatus;
import com.legosoft.cqrs.eventsourcing.command.grupoempresarial.CreateGrupoCommand;
import com.legosoft.cqrs.eventsourcing.event.grupoempresarial.GrupoCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.EnumString;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "GrupoEmpresarial")
@Aggregate
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrupoEmpresarial implements Serializable {

    @Id
    @GeneratedValue
    @AggregateIdentifier
    private Long id;

    private String nombreGrupo;

    private Date fechaCreacion;

    private GrupoEmpresarialEstatus estatus;

    @Relationship(type = "ADMINISTRA", direction = Relationship.INCOMING)
    private Usuario usuario;

    @Relationship(type = "ALLOW", direction = Relationship.INCOMING)
    private Set<Agente> agentes = new HashSet<>();


    @CommandHandler
    public GrupoEmpresarial(CreateGrupoCommand createGrupoCommand){
        AggregateLifecycle.apply(new GrupoCreatedEvent(createGrupoCommand.getId(), createGrupoCommand.getNombreGrupo(), createGrupoCommand.getFechaCreacion(), createGrupoCommand.getEstatus()));
    }

    @EventSourcingHandler
    public void on(GrupoCreatedEvent grupoCreatedEvent){
        this.id = grupoCreatedEvent.getId();
        this.nombreGrupo = grupoCreatedEvent.getNombreGrupo();
        this.fechaCreacion = grupoCreatedEvent.getFechaCreacion();
        this.estatus = grupoCreatedEvent.getEstatus();
    }

}
