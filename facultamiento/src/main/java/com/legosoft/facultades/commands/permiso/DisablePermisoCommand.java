package com.legosoft.facultades.commands.permiso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisablePermisoCommand {

    @TargetAggregateIdentifier
    private Long idPermiso;

}
