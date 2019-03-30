package com.legosoft.facultades.services;

import com.legosoft.facultades.models.Permiso;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PermisoService {

    /**
     *
     * @param permiso
     * @return
     */
    public ResponseEntity savePermiso(Permiso permiso);

    /**
     *
     * @param permiso
     * @return
     */
    CompletableFuture<String> updatePermiso(Permiso permiso);

    /**
     *
     * @param permiso
     */
    CompletableFuture<String> deshabilitarPermiso(Permiso permiso);

    /**
     *
     * @param permisoId
     * @return
     */
    void findPermisobyId(Long permisoId);

    /**
     *
     * @return
     */
    void findAll();

}
