package com.legosoft.facultades.commands.rol;

import com.legosoft.facultades.models.Permiso;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRolCommand {

    @TargetAggregateIdentifier
    private Long idRol;

    private String nombre;

    private Boolean isActivo;

    private Set<Permiso> permisos;

}
