package com.legosoft.cqrs.service;

import com.legosoft.cqrs.models.Permiso;

import java.util.concurrent.CompletableFuture;

public interface PermisoService {

    /**
     * Servicio quer persiste el evento crear permiso y la entidad cuando se genera el comando
     * @param permiso
     * @return
     */
    public CompletableFuture<String> createCommandPermiso(Permiso permiso);

    /**
     * Servicio que guarda o actualiza un permiso
     * @param permiso
     * @return
     */
    public Permiso mergePermiso(Permiso permiso);

    /**
     * Servicio que buscar un permiso por nombre
     * @param nombrePermiso
     * @return
     */
    public Permiso findPermisoByNombre(String nombrePermiso);
}
