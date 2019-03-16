package com.legosoft.cqrs.service;

import com.legosoft.cqrs.models.Compania;

public interface CompaniaService {

    /**
     * Servicio que crea el comando de guardar el agente y registra el evento
     * @param compania
     * @return
     */
    public Compania createCommandCompania(Compania compania);

    public Compania findCompaniaByNombre(String nombreAgente);
}
