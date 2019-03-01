package com.legosoft.cqrs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
public class AgenteDto implements Serializable {

    private Long idAgente;

    private String idAgenteEvent;

    private String nombreAgente;

    private Date fechaCracion;

    private boolean activo;

}
