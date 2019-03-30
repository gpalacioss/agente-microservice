package com.legosoft.facultades.models;

import com.legosoft.facultades.commands.perfil.CreatePerfilCommand;
import com.legosoft.facultades.commands.perfil.DisablePerfilCommand;
import com.legosoft.facultades.commands.perfil.UpdatePerfilCommand;
import com.legosoft.facultades.events.perfil.PerfilCreatedEvent;
import com.legosoft.facultades.events.perfil.PerfilDisableEvent;
import com.legosoft.facultades.events.perfil.PerfilUpdateEvent;
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
import java.util.HashSet;
import java.util.Set;

@Aggregate
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "perfil")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @AggregateIdentifier
    private Long idPerfil;


    private String nombre;

    private Boolean activo;

    private Boolean isAdmin;

    @Transient
    private String tipo;


    @OneToMany(targetEntity = Rol.class, mappedBy = "perfil", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
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

    @CommandHandler
    protected void on(UpdatePerfilCommand command){

        AggregateLifecycle.apply(new PerfilUpdateEvent(
                command.getIdPerfilEvent(), command.getNombre(),
                command.getActivo(),command.getIsAdmin()
        ));

    }

    @EventSourcingHandler
    protected void on(PerfilUpdateEvent event){
        this.idPerfil = event.getIdPerfilEvent();
        this.nombre = event.getNombre();
        this.activo = event.getActivo();
        this.isAdmin = event.getIsAdmin();
    }

    @CommandHandler
    protected void on(DisablePerfilCommand command){

        AggregateLifecycle.apply(new PerfilDisableEvent(
                command.getIdPermiso(),false
        ));

    }

    @EventSourcingHandler
    protected void on(PerfilDisableEvent event){
        this.activo = event.getActivo();
    }
}
