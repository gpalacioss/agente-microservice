package com.legosoft.facultades.models;

import com.legosoft.facultades.commands.permiso.CreatePermisoCommand;
import com.legosoft.facultades.commands.permiso.DisablePermisoCommand;
import com.legosoft.facultades.commands.permiso.UpdatePermisoCommand;
import com.legosoft.facultades.events.permiso.PermisoCreatedEvent;
import com.legosoft.facultades.events.permiso.PermisoDisableEvent;
import com.legosoft.facultades.events.permiso.PermisoUpdateEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Aggregate
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Entity
@Table(name = "permiso")
public class Permiso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @AggregateIdentifier
    private Long idPermiso;

    private String nombre;

    private Boolean permisoAcme;

    private String descripcion;

    private Boolean permisoInicioSesion;

    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "idRol", referencedColumnName = "idRol")
    private Rol rol;

    @Transient
    private String tipo;

    @CommandHandler
    public Permiso(CreatePermisoCommand command) {

        AggregateLifecycle.apply(new PermisoCreatedEvent(command.getIdPermiso(),command.getNombre(),command.getPermisoAcme(),
                command.getDescripcion(),command.getPermisoInicioSesion(),command.getIsActivo()));
    }

    @EventSourcingHandler
    protected void on(PermisoCreatedEvent event){
        this.idPermiso = event.getIdPermisoEvent();
        this.nombre = event.getNombre();
        this.permisoAcme = event.getPermisoAcme();
        this.descripcion = event.getDescripcion();
        this.permisoInicioSesion = event.getPermisoInicioSesion();
        this.activo = event.getIsActivo();
    }

    @CommandHandler
    protected void on(UpdatePermisoCommand command){
        AggregateLifecycle.apply(new PermisoUpdateEvent(
               command.getIdPermisoEvent(),command.getNombre(),
                command.getPermisoAcme(),command.getDescripcion(),command.getPermisoInicioSesion(),command.getIsActivo()));
    }

    @EventSourcingHandler
    protected void on(PermisoUpdateEvent event){
        this.nombre = event.getNombre();
        this.permisoAcme = event.getPermisoAcme();
        this.descripcion = event.getDescripcion();
        this.permisoInicioSesion = event.getPermisoInicioSesion();
        this.activo = event.getIsActivo();
    }

    @CommandHandler
    protected void on(DisablePermisoCommand command){
        AggregateLifecycle.apply(new PermisoDisableEvent(
                command.getIdPermiso(),false
        ));
    }

    @EventSourcingHandler
    protected void on(PermisoDisableEvent event){
        this.activo = event.getActivo();
    }


}
