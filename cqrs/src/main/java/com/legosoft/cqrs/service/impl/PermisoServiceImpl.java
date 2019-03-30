package com.legosoft.cqrs.service.impl;

import com.legosoft.cqrs.eventsourcing.command.permiso.CreatePermisoCommand;
import com.legosoft.cqrs.models.Permiso;
import com.legosoft.cqrs.repository.PermisoRepository;
import com.legosoft.cqrs.service.PermisoService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service("permisoService")
public class PermisoServiceImpl implements PermisoService {

    private CommandGateway commandGateway ;

    @Autowired
    private PermisoRepository permisoRepository;

    public PermisoServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public CompletableFuture<String> createCommandPermiso(Permiso permiso){
        Permiso pr = permisoRepository.findByNombre(permiso.getNombre());
        if (pr != null){
            permiso.setIdPermiso(pr.getIdPermiso());
        }else{
            permiso.setIdPermiso(null);
        }

        permiso = mergePermiso(permiso);
        CreatePermisoCommand command = new CreatePermisoCommand(permiso.getIdPermiso(),permiso.getNombre(), permiso.getTipo(), permiso.getDescripcion(), permiso.getPermisoInicioSesion(), permiso.getActivo());
        return commandGateway.send(command);
    }

    public Permiso mergePermiso(Permiso permiso){
        return permisoRepository.save(permiso);
    }

    public Permiso findPermisoByNombre(String nombrePermiso){
        return permisoRepository.findByNombre(nombrePermiso);
    }

}
