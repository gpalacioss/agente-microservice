package com.legosoft.cqrs.models;

import com.legosoft.cqrs.eventsourcing.command.rol.CreateRolCommand;
import com.legosoft.cqrs.eventsourcing.event.rol.RolCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Aggregate
@Data
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity(label = "Rol")
public class Rol {

    @Id
    @GeneratedValue()
    @AggregateIdentifier
    private Long idRol;

    private String nombre;

    @Relationship(type = "HAS_PERMISO")
    private Set<Permiso> permisos = new HashSet<>();

    private Boolean activo;

    @CommandHandler
    public Rol(CreateRolCommand command){
        AggregateLifecycle.apply(new RolCreatedEvent(command.getIdRol(),command.getNombre(),
                command.getIsActivo()));
    }

    @EventSourcingHandler
    protected void on(RolCreatedEvent event){
        this.idRol = event.getIdRol();
        this.nombre = event.getNombre();
        this.activo = event.getActivo();
    }

//    @CommandHandler
//    protected void on(UpdateRolCommand command){
//
//        AggregateLifecycle.apply(new RolUpdateEvent(
//                command.getIdRolEvent(),
//                command.getNombre(),command.getIsActivo()
//        ));
//    }
//
//    @EventSourcingHandler
//    protected void on(RolUpdateEvent event){
//        this.nombre = event.getNombre();
//        this.activo = event.getActivo();
//    }
//
//    @CommandHandler
//    protected void on(DisablePermisoCommand command){
//
//        AggregateLifecycle.apply(new RolDisableEvent(
//                command.getIdPermiso(),false
//                ));
//    }
//
//    @EventSourcingHandler
//    protected void on(RolDisableEvent event){
//        this.activo = event.getActivo();
//    }

}
