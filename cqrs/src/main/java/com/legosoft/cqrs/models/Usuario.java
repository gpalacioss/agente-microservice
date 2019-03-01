package com.legosoft.cqrs.models;

import com.legosoft.cqrs.eventsourcing.command.usuario.CreateUsuarioCommand;
import com.legosoft.cqrs.eventsourcing.event.usuario.UsuarioCreatedEvent;
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

import java.io.Serializable;

@NodeEntity(label = "Usuario")
@Aggregate
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @AggregateIdentifier
    private String idEvent;

    private String nombreUsuario;

    private String nombreCompleto;

    private String email;

    private String password;

    private boolean administrador;

    private boolean activo;

    @CommandHandler
    public Usuario(CreateUsuarioCommand createUsuarioCommand){
        AggregateLifecycle.apply(new UsuarioCreatedEvent(createUsuarioCommand.getIdEvent(), createUsuarioCommand.getNombreUsuario(), createUsuarioCommand.getNombreCompleto(),
                createUsuarioCommand.getEmail(), createUsuarioCommand.getPassword(), createUsuarioCommand.isAdministrador(), createUsuarioCommand.isActivo()));
    }

    @EventSourcingHandler
    public void on(UsuarioCreatedEvent usuarioCreatedEvent){

        this.idEvent = usuarioCreatedEvent.getIdEvent();
        this.nombreUsuario = usuarioCreatedEvent.getNombreUsuario();
        this.nombreCompleto = usuarioCreatedEvent.getNombreCompleto();
        this.email = usuarioCreatedEvent.getEmail();
        this.password = usuarioCreatedEvent.getPassword();
        this.administrador = usuarioCreatedEvent.isAdministrador();
        this.activo = usuarioCreatedEvent.isActivo();

    }
}
