package com.legosoft.cqrs.eventsourcing.command.permiso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.AggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePermisoCommand {

    @AggregateIdentifier
    private Long idPermiso;

    private String nombre;

    private Boolean tipo;

    private String descripcion;

    private Boolean permisoInicioSesion;

    private Boolean isActivo;

}
