package com.legosoft.agentes.eventsourcing.event.agente;

import com.legosoft.agentes.model.Compania;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorRelationGrupoCompaniaEvent {

    private Long idEvent;

    private Compania compania;
}
