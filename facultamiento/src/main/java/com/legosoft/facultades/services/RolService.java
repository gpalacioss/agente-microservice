package com.legosoft.facultades.services;

import com.legosoft.facultades.models.Rol;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RolService {

    /**
     *
     * @param rol
     * @return Rol
     */
    public ResponseEntity saveRol(Rol rol);

    /**
     *
     * @param rol
     * @return
     */
    CompletableFuture<String> updateRol(Rol rol);

    /**
     *
     * @param rol
     */
    CompletableFuture<String> deshabilitarRol(Rol rol);

    /**
     *
     * @param roleId
     * @return
     */
    void findRoleById(Long roleId);

    /**
     *
     * @return
     */
    void findAll();
}
