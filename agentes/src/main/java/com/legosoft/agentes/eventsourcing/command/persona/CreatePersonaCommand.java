package com.legosoft.agentes.eventsourcing.command.persona;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePersonaCommand {

    @TargetAggregateIdentifier
    private String idPersona;

    private String nombre;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String razonSocial;

    private Date fechaNacimiento;

    private String telefono;


}
