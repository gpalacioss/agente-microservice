package com.legosoft.cqrs.service;

import com.legosoft.cqrs.models.Rol;

import java.util.concurrent.CompletableFuture;

public interface RolService {

    /**
     * Servicio que genera el evento de crear rol y la entidad cuando se ejecuta el comando
     * @param rol
     * @return
     */
    public CompletableFuture<String> createCommandRol(Rol rol);

    /**
     * Servicio que guarda y actualiza el rol
     * @param rol
     * @return
     */
    public Rol mergeRol(Rol rol);

    public Rol findRolByNombreRol(String nombreRol);
}
