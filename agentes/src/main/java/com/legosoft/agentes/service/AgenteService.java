package com.legosoft.agentes.service;

import com.legosoft.agentes.model.Agente;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AgenteService {

    public CompletableFuture<String> createCommandAgente(Agente agente);

    /**
     *
     * Servicio que consulta a un agente por su id
     * @param idAgente
     * @return
     */
    public Agente findAgenteById(Long idAgente);

    /**
     * Servicio que consulta todos los agetes
     * @return
     */
    public List<Agente> findAllAgentes();

    /**
     * Servicio que guarda o actualiza la informacion de un agente
     * @param agente
     * @return
     */
    public Agente saveOrUpdate(Agente agente);

    /**
     * Servicio que elimina el agente
     * @param agente
     */
    public void deleteAgente(Agente agente);
}
