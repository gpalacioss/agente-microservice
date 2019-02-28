package com.legosoft.agentes.eventsourcing.aggregate;

import com.legosoft.agentes.enums.TipoPersona;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;


import java.util.Date;

@Aggregate
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Persona {

    @AggregateIdentifier
    private String idPersona;

    private String nombre;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String razonSocial;

    private Date fechaNacimiento;

    private String telefono;

    private String email;

    private String rfc;

    private boolean estatus;

    private TipoPersona tipoPersona;

    private boolean activo;


//    //Comando Agregar Persona que disparara el evento agregar persona
//    public Persona(CreatePersonaCommand createPersonaCommand) {
//        AggregateLifecycle.apply(new PersonaCreatedEvent())
//    }
//
//    /**
//     * Apartados de Eventos que genera el Agregado persona
//     * @param event
//     */
//    @EventSourcingHandler
//    public void on(AddPersonEvent event){
//        this.idPersona = event.getIdPersona();
//        this.nombre = event.getNombre();
//        this.apellidoPaterno = event.getApellidoPaterno();
//        this.apellidoMaterno =  event.getApellidoMaterno();
//        this.razonSocial = event.getRazonSocial();
//        this.fechaNacimiento = event.getFechaNacimiento();
//        this.telefono = event.getTelefono();
//        this.email = event.getEmail();
//        this.rfc = event.getRfc();
//        this.estatus = event.isEstatus();
//        this.tipoPersona = event.getTipoPersona();
//        this.activo = event.isActivo();
//    }
}
