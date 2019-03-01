package com.legosoft.cqrs.eventsourcing.event.grupoempresarial;

import com.legosoft.cqrs.enums.GrupoEmpresarialEstatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrupoCreatedEvent {

    private String idEvent;

    private String nombreGrupo;

    private Date fechaCreacion;

    private GrupoEmpresarialEstatus estatus;
}
