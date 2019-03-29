package com.legosoft.agentes.eventsourcing.event.agente;

import com.legosoft.agentes.model.Compania;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateRabbitErrorCompaniaEvent {

    private Long idEvent;

    private Compania compania;
}
