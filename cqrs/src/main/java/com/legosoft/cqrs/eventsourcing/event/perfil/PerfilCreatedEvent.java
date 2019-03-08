package com.legosoft.cqrs.eventsourcing.event.perfil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilCreatedEvent {

    private Long idPerfil;

    private String nombre;

    private Boolean activo;

    private Boolean isAdmin;

}
