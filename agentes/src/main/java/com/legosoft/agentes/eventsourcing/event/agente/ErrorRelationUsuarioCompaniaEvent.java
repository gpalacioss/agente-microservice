package com.legosoft.agentes.eventsourcing.event.agente;

import com.legosoft.agentes.model.Compania;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorRelationUsuarioCompaniaEvent {

    private Long idEvent;

    private Compania compania;
}
