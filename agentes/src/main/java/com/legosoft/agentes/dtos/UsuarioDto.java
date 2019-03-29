package com.legosoft.agentes.dtos;

import com.legosoft.agentes.model.Compania;
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


    private Long idUsuario;
    private String nombreUsuario;
    private String password;
    private String email;
    private boolean administrador;
    private boolean activo;
    private String tipo = "Usuario";
    private String estatus;

    private Set<Compania> companias = new HashSet<>();

}
