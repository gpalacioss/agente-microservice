package com.legosoft.agentes.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class AgenteDto {

    private Long idAgente;

    private String nombreAgente;

    private Date fechaCracion;

    private boolean activo;
}
