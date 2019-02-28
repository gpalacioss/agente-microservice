package com.legosoft.agentes.eventsourcing.command.agente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAgenteCommand {

    private Long idAgente;

    private String nombreAgente;

    private Date fechaCracion;

    private boolean activo;

}
