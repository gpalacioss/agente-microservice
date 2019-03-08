package com.legosoft.cqrs.eventsourcing.command.rol;

import com.legosoft.cqrs.models.Permiso;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRolCommand {

    @TargetAggregateIdentifier
    private Long idRol;

    private String nombre;

    private Boolean isActivo;

    private Set<Permiso> permisos;

}
