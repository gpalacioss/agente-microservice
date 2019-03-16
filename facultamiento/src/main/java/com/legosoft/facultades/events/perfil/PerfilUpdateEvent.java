package com.legosoft.facultades.events.perfil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilUpdateEvent {

    private Long idPerfilEvent;

    private String nombre;

    private Boolean activo;

    private Boolean isAdmin;

}
