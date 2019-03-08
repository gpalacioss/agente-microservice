package com.legosoft.cqrs.eventsourcing.event.rol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolCreatedEvent {

    private Long idRol;

    private String nombre;

    private Boolean activo;

}
