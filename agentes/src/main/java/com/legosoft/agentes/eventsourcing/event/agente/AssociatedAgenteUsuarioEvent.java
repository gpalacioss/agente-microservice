package com.legosoft.agentes.eventsourcing.event.agente;

import lombok.*;

import java.util.Date;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssociatedAgenteUsuarioEvent {

    private Long idAgente;

    private String nombreAgente;

    private Date fechaCreacion;

    private boolean activo;

    private String estatus;

}
