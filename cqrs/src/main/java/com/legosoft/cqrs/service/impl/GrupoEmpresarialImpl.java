package com.legosoft.cqrs.service.impl;

import com.legosoft.cqrs.eventsourcing.command.grupoempresarial.CreateGrupoCommand;
import com.legosoft.cqrs.models.GrupoEmpresarial;
import com.legosoft.cqrs.repository.GrupoEmpresarialRepository;
import com.legosoft.cqrs.service.GrupoEmpresarialService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service("grupoEmpresarialService")
public class GrupoEmpresarialImpl implements GrupoEmpresarialService {

    private CommandGateway commandGateway ;

    public GrupoEmpresarialImpl(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

    @Autowired
    private GrupoEmpresarialRepository grupoEmpresarialRepository;

    public CompletableFuture<String> createCommandGrupo(GrupoEmpresarial grupoEmpresarial){
        String id = UUID.randomUUID().toString();
        grupoEmpresarial.setIdEvent(id);
        grupoEmpresarial.setId(null);
        CreateGrupoCommand createGrupoCommand = new CreateGrupoCommand(grupoEmpresarial.getIdEvent(), grupoEmpresarial.getNombreGrupo(), grupoEmpresarial.getFechaCreacion(), grupoEmpresarial.getEstatus());
        saveGrupo(grupoEmpresarial);

        //TODO: Llamar cola si se necesita producir algo
        return commandGateway.send(createGrupoCommand);
    }

    public GrupoEmpresarial saveGrupo(GrupoEmpresarial grupoEmpresarial){
        return grupoEmpresarialRepository.save(grupoEmpresarial);
    }
}
