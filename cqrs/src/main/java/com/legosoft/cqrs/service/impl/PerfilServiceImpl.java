package com.legosoft.cqrs.service.impl;

import com.legosoft.cqrs.eventsourcing.command.perfil.CreatePerfilCommand;
import com.legosoft.cqrs.models.Perfil;
import com.legosoft.cqrs.models.Rol;
import com.legosoft.cqrs.repository.PerfilRepository;
import com.legosoft.cqrs.service.PerfilService;
import com.legosoft.cqrs.service.RolService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service("perfilService")
public class PerfilServiceImpl implements PerfilService {

    private CommandGateway commandGateway ;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private RolService rolService;

    public PerfilServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public CompletableFuture<String> createCommandPerfil(Perfil perfil){
        Set<Rol> lstRoles = new HashSet<>();
        perfil.getRolesPerfil().forEach(r -> {
            Rol rl = rolService.findRolByNombreRol(r.getNombre());
            lstRoles.add(rl);
        });

        perfil.setIdPerfil(null);
        perfil.setRolesPerfil(lstRoles);
        perfil = mergePerfil(perfil);

        CreatePerfilCommand command = new CreatePerfilCommand(perfil.getIdPerfil(),perfil.getNombre(),perfil.getActivo(),perfil.getIsAdmin(),perfil.getRolesPerfil());
        return commandGateway.send(command);
    }

    public Perfil mergePerfil(Perfil perfil){
        return perfilRepository.save(perfil);
    }

    public Perfil finsPerfilByNombre(String nombrePerfil){
        return perfilRepository.findByNombre(nombrePerfil);
    }

}
