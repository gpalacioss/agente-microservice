package com.legosoft.cqrs.eventsourcing.event.permiso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermisoCreatedEvent {

    private Long idPermiso;

    private String nombre;

    private Boolean tipo;

    private String descripcion;

    private Boolean permisoInicioSesion;

    private Boolean isActivo;

}
