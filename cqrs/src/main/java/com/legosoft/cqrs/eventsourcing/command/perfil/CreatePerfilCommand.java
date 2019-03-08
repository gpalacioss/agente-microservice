package com.legosoft.cqrs.eventsourcing.command.perfil;

import com.legosoft.cqrs.models.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.AggregateIdentifier;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePerfilCommand {

    @AggregateIdentifier
    private Long idPerfil;

    private String nombre;

    private Boolean activo;

    private Boolean isAdmin;

    private Set<Rol> rolesPerfil;

}
