package com.legosoft.cqrs.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Transient;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Aggregate
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@NodeEntity(label = "Compania")
public class Compania implements Serializable {

    @Id
    @AggregateIdentifier
    @GeneratedValue
    private Long idCompania;


    private String nombreCompania;


    private String nombrePersona;


    private String direccion;


    private String email;


    private Date fechaCreacion;


    private boolean activo;


    private String estatus;


    @Transient
    private Long fechaCreacionMillis;

}
