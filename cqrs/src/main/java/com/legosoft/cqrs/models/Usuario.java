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
import org.neo4j.ogm.annotation.Relationship;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "Usuario")
@Aggregate
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable {

    @Id
    @GeneratedValue
    @AggregateIdentifier
    private Long id;

    private String nombreUsuario;

    private String nombreCompleto;

    private String email;

    private String password;

    private boolean administrador;

    private boolean activo;

    @Relationship(type = "ADMINISTRA")
    private Set<Compania> companias = new HashSet<>();

    @Relationship(type = "HAS_PERFIL")
    private Set<Perfil> perfiles = new HashSet<>();

    @CommandHandler
    public Usuario(CreateUsuarioCommand createUsuarioCommand){
        AggregateLifecycle.apply(new UsuarioCreatedEvent(createUsuarioCommand.getId(), createUsuarioCommand.getNombreUsuario(), createUsuarioCommand.getNombreCompleto(),
                createUsuarioCommand.getEmail(), createUsuarioCommand.getPassword(), createUsuarioCommand.isAdministrador(), createUsuarioCommand.isActivo()));
    }

    @EventSourcingHandler
    public void on(UsuarioCreatedEvent usuarioCreatedEvent){

        this.id = usuarioCreatedEvent.getId();
        this.nombreUsuario = usuarioCreatedEvent.getNombreUsuario();
        this.nombreCompleto = usuarioCreatedEvent.getNombreCompleto();
        this.email = usuarioCreatedEvent.getEmail();
        this.password = usuarioCreatedEvent.getPassword();
        this.administrador = usuarioCreatedEvent.isAdministrador();
        this.activo = usuarioCreatedEvent.isActivo();

    }
}
