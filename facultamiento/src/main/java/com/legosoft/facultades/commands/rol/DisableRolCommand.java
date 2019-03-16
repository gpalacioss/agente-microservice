package com.legosoft.facultades.commands.rol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisableRolCommand {

    @TargetAggregateIdentifier
    private Long idRol;

}
