package com.legosoft.agentes.model;


import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "agente")
public class Agente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AGENTE")
    private Long idAgente;

    @Column(name = "NOMBRE_AGENTE")
    private String nombreAgente;

    @Column(name = "FECHA_CREACION")
    private Date fechaCracion;

    @Column(name = "ACTIVO")
    private boolean activo;

    public Long getIdAgente() {
        return idAgente;
    }

    public void setIdAgente(Long idAgente) {
        this.idAgente = idAgente;
    }

    public String getNombreAgente() {
        return nombreAgente;
    }

    public void setNombreAgente(String nombreAgente) {
        this.nombreAgente = nombreAgente;
    }

    public Date getFechaCracion() {
        return fechaCracion;
    }

    public void setFechaCracion(Date fechaCracion) {
        this.fechaCracion = fechaCracion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
