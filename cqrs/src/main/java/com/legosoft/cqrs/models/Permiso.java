package com.legosoft.cqrs.models;

import com.legosoft.cqrs.eventsourcing.command.permiso.CreatePermisoCommand;
import com.legosoft.cqrs.eventsourcing.event.permiso.PermisoCreatedEvent;
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
import org.neo4j.ogm.annotation.Transient;

import java.io.Serializable;

@Data
@Aggregate
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@NodeEntity(label = "Permiso")
public class Permiso implements Serializable {

    @Id
    @GeneratedValue()
    @AggregateIdentifier
    private Long idPermiso;

    private String nombre;

    private Boolean tipo;

    private String descripcion;

    private Boolean permisoInicioSesion;

    private Boolean activo;

    @CommandHandler
    public Permiso(CreatePermisoCommand command) {

        AggregateLifecycle.apply(new PermisoCreatedEvent(command.getIdPermiso(),command.getNombre(),command.getTipo(),
                command.getDescripcion(),command.getPermisoInicioSesion(),command.getIsActivo()));
    }

    @EventSourcingHandler
    protected void on(PermisoCreatedEvent event){
        this.idPermiso = event.getIdPermiso();
        this.nombre = event.getNombre();
        this.tipo = event.getTipo();
        this.descripcion = event.getDescripcion();
        this.permisoInicioSesion = event.getPermisoInicioSesion();
        this.activo = event.getIsActivo();
    }

//    @CommandHandler
//    protected void on(UpdatePermisoCommand command){
//        AggregateLifecycle.apply(new PermisoUpdateEvent(
//               command.getIdPermisoEvent(),command.getNombre(),
//                command.getTipo(),command.getDescripcion(),command.getPermisoInicioSesion(),command.getIsActivo()));
//    }
//
//    @EventSourcingHandler
//    protected void on(PermisoUpdateEvent event){
//        this.nombre = event.getNombre();
//        this.tipo = event.getTipo();
//        this.descripcion = event.getDescripcion();
//        this.permisoInicioSesion = event.getPermisoInicioSesion();
//        this.activo = event.getIsActivo();
//    }
//
//    @CommandHandler
//    protected void on(DisablePermisoCommand command){
//        AggregateLifecycle.apply(new PermisoDisableEvent(
//                command.getIdPermiso(),false
//        ));
//    }
//
//    @EventSourcingHandler
//    protected void on(PermisoDisableEvent event){
//        this.activo = event.getActivo();
//    }


}
