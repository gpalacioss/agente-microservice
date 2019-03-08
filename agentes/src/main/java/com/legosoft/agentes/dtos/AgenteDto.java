package com.legosoft.agentes.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AgenteDto {

    private Long idAgente;

    private String nombreAgente;

    private Date fechaCreacion;

    private boolean activo;

    private String estatus;

}
