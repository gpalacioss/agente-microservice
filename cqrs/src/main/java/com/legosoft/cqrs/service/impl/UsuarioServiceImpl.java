package com.legosoft.cqrs.service.impl;

import com.legosoft.cqrs.eventsourcing.command.usuario.CreateUsuarioCommand;
import com.legosoft.cqrs.models.Usuario;
import com.legosoft.cqrs.repository.UsuarioRepository;
import com.legosoft.cqrs.service.UsuarioService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

    private CommandGateway commandGateway ;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }


    public CompletableFuture<String> createCommandUsuario(Usuario usuario){
        String id = UUID.randomUUID().toString();
        usuario.setIdEvent(id);
        CreateUsuarioCommand createUsuarioCommand = new CreateUsuarioCommand(usuario.getIdEvent(), usuario.getNombreUsuario(), usuario.getNombreCompleto(), usuario.getEmail(), usuario.getPassword(), usuario.isAdministrador(), usuario.isActivo());
        usuario.setId(null);
        saveUsuario(usuario);
        //TODO: Llamar cola si se necesita producir algo
        return commandGateway.send(createUsuarioCommand);
    }

    public Usuario saveUsuario(Usuario usuario){
        return  usuarioRepository.save(usuario);
    }

    public Usuario findUsuarioByNombre(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }
}
