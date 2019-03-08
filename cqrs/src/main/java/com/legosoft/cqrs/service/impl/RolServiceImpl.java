package com.legosoft.cqrs.service.impl;

import com.legosoft.cqrs.eventsourcing.command.rol.CreateRolCommand;
import com.legosoft.cqrs.models.Permiso;
import com.legosoft.cqrs.models.Rol;
import com.legosoft.cqrs.repository.RolRepository;
import com.legosoft.cqrs.service.PermisoService;
import com.legosoft.cqrs.service.RolService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service("rolService")
public class RolServiceImpl implements RolService {

    private CommandGateway commandGateway;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PermisoService permisoService;

    public RolServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public CompletableFuture<String> createCommandRol(Rol rol){
        Set<Permiso> lstPermisos = new HashSet<>();
       rol.setIdRol(null);
       rol.getPermisos().forEach(p -> {
           Permiso per = permisoService.findPermisoByNombre(p.getNombre());
           lstPermisos.add(per);
       });
       rol.setPermisos(lstPermisos);

       rol = mergeRol(rol);
        CreateRolCommand command = new CreateRolCommand(rol.getIdRol(),rol.getNombre(),rol.getActivo(), rol.getPermisos());
        return commandGateway.send(command);
    }

    public Rol mergeRol(Rol rol){
        return rolRepository.save(rol);
    }

    public Rol findRolByNombreRol(String nombreRol){
        return rolRepository.findByNombre(nombreRol);
    }
}
