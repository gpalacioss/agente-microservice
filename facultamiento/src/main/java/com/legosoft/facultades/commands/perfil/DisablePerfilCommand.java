package com.legosoft.facultades.commands.perfil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisablePerfilCommand {

    @TargetAggregateIdentifier
    private Long idPermiso;

}
