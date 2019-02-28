package com.legosoft.agentes.dtos;

import com.legosoft.agentes.model.Agente;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageColas {

    private String mensaje;

    private String correo;

    private Agente agente;
}
