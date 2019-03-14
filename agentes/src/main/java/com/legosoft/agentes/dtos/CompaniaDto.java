package com.legosoft.agentes.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CompaniaDto {


    private String nombreCompania;

    private String nombrePersona;

    private Date fechaCreacion;

    private String direccion;

    private String email;

    private String nombreGrupo;


}
