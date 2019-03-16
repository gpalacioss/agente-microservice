package com.legosoft.facultades.events.rol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolDisableEvent {

    private Long idRol;

    private Boolean activo;

}
