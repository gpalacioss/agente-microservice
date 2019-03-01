package com.legosoft.cqrs.service;

import com.legosoft.cqrs.models.Agente;

import java.util.concurrent.CompletableFuture;

public interface AgenteService {

    /**
     * Servicio que crea el comando de guardar el agente y registra el evento
     * @param agente
     * @return
     */
    public CompletableFuture<String> createCommandAgente(Agente agente);
}
