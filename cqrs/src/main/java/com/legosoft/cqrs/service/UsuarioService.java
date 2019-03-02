package com.legosoft.cqrs.service;

import com.legosoft.cqrs.models.Usuario;

import java.util.concurrent.CompletableFuture;

public interface UsuarioService {

    public CompletableFuture<String> createCommandUsuario(Usuario usuario);

    /**
     * Servicio que busca un usuario por nombre
     * @param nombreUsuario
     * @return
     */
    public Usuario findUsuarioByNombre(String nombreUsuario);

}
