package com.legosoft.facultades.events.rol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolUpdateEvent {

    private Long idRol;

    private String nombre;

    private Boolean activo;

}
