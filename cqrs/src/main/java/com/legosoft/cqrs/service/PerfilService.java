package com.legosoft.cqrs.service;

import com.legosoft.cqrs.models.Perfil;

import java.util.concurrent.CompletableFuture;

public interface PerfilService {

    /**
     * Servicio que genera el evento de crear perfil y la entidad cuando se ejecuta el comando
     * @param perfil
     * @return
     */
    public CompletableFuture<String> createCommandPerfil(Perfil perfil);

    public Perfil mergePerfil(Perfil perfil);

    /**
     * Servicio que hace una busqueda por nombre de perfil
     * @param nombrePerfil
     * @return
     */
    public Perfil finsPerfilByNombre(String nombrePerfil);
}
