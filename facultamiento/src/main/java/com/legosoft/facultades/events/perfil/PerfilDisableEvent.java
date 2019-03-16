package com.legosoft.facultades.events.perfil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDisableEvent {

    private Long idPerfil;

    private Boolean activo;

}
