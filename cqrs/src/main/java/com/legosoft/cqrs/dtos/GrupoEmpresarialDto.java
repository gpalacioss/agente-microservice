package com.legosoft.cqrs.dtos;

import com.legosoft.cqrs.enums.GrupoEmpresarialEstatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
public class GrupoEmpresarialDto implements Serializable {

    private Long id;

    private String idEvent;

    private String nombreGrupo;

    private Date fechaCreacion;

    private GrupoEmpresarialEstatus estatus;

}
