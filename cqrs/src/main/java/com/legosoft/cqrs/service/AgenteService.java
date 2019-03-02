package com.legosoft.cqrs.service;

import com.legosoft.cqrs.models.Agente;

import java.util.concurrent.CompletableFuture;

public interface AgenteService {

    /**
     * Servicio que crea el comando de guardar el agente y registra el evento
     * @param agente
     * @return
     */
    public Agente createCommandAgente(Agente agente);

    public Agente findAgenteByNombreAgente(String nombreAgente);
}
