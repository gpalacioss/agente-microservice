package com.legosoft.facultades.services;

import com.legosoft.facultades.models.Perfil;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PerfilService {

    /**
     *
     * @param perfil
     * @return
     */
    public ResponseEntity savePefil(Perfil perfil);

    /**
     *
     * @param perfil
     * @return
     */
    CompletableFuture<String> updatePerfil(Perfil perfil);


    CompletableFuture<String> deshabilitarPerfil(Perfil perfil);


    void findPerfilById(Long perfilId);


    void findAll();

}
