package com.legosoft.agentes.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@Aggregate
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto implements Serializable {


    private Long id;
    @AggregateIdentifier
    private String idEvent;

    private String nombreUsuario;

    private String nombreCompleto;

    private String email;

    private String password;

    private boolean administrador;

    private boolean activo;

    private Set<AgenteDto> agentes = new HashSet<>();

}
