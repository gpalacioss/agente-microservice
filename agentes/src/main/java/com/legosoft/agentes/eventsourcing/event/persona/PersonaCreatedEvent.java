package com.legosoft.agentes.eventsourcing.event.persona;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaCreatedEvent {

    private String idPersona;

    private String nombre;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String razonSocial;

    private Date fechaNacimiento;

    private String telefono;


}
