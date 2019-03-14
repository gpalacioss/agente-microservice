package com.legosoft.agentes.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Aggregate
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Table(name = "compania")
public class Compania implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compania")
    private Long idCompania;

    @Column(name = "nombre_compania")
    private String nombreCompania;

    @Column(name = "nombre_persona")
    private String nombrePersona;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "email")
    private String email;


    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "activo")
    private boolean activo;

    @Column(name = "estatus")
    private String estatus;

    @Transient
    private String nombreGrupo;

    @Transient
    private String tipo = "compania";


}
