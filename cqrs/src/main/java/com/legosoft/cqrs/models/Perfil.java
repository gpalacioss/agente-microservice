package com.legosoft.cqrs.models;

import com.legosoft.cqrs.eventsourcing.command.perfil.CreatePerfilCommand;
import com.legosoft.cqrs.eventsourcing.event.perfil.PerfilCreatedEvent;
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
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@Aggregate
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity(label = "Perfil")
public class Perfil {

    @Id
    @GeneratedValue()
    @AggregateIdentifier
    private Long idPerfil;

    private String nombre;

    private Boolean activo;

    private Boolean isAdmin;

    @Relationship(type = "HAS_ROL")
    private Set<Rol> rolesPerfil = new HashSet<>();

    @CommandHandler
    public Perfil(CreatePerfilCommand command){

        AggregateLifecycle.apply(new PerfilCreatedEvent(
                command.getIdPerfil(), command.getNombre(),
                command.getActivo(),command.getIsAdmin()
        ));
    }

    @EventSourcingHandler
    protected void on(PerfilCreatedEvent event){
        this.idPerfil = event.getIdPerfil();
        this.nombre = event.getNombre();
        this.activo = event.getActivo();
        this.isAdmin = event.getIsAdmin();
    }

//    @CommandHandler
//    protected void on(UpdatePerfilCommand command){
//
//        AggregateLifecycle.apply(new PerfilUpdateEvent(
//                command.getIdPerfilEvent(), command.getNombre(),
//                command.getActivo(),command.getIsAdmin()
//        ));
//
//    }
//
//    @EventSourcingHandler
//    protected void on(PerfilUpdateEvent event){
//        this.idPerfilEvent = event.getIdPerfilEvent();
//        this.nombre = event.getNombre();
//        this.activo = event.getActivo();
//        this.isAdmin = event.getIsAdmin();
//    }
//
//    @CommandHandler
//    protected void on(DisablePerfilCommand command){
//
//        AggregateLifecycle.apply(new PerfilDisableEvent(
//                command.getIdPermiso(),false
//        ));
//
//    }
//
//    @EventSourcingHandler
//    protected void on(PerfilDisableEvent event){
//        this.activo = event.getActivo();
//    }
}
