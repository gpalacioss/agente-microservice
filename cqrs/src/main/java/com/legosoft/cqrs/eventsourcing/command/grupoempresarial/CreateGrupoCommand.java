package com.legosoft.cqrs.eventsourcing.command.grupoempresarial;

import com.legosoft.cqrs.enums.GrupoEmpresarialEstatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGrupoCommand {

    @TargetAggregateIdentifier
    private Long id;

    private String nombreGrupo;

    private Date fechaCreacion;

    private GrupoEmpresarialEstatus estatus;

}
