package com.legosoft.cqrs.service.impl;

import com.legosoft.cqrs.eventsourcing.command.grupoempresarial.CreateGrupoCommand;
import com.legosoft.cqrs.models.Agente;
import com.legosoft.cqrs.models.GrupoEmpresarial;
import com.legosoft.cqrs.repository.GrupoEmpresarialRepository;
import com.legosoft.cqrs.service.AgenteService;
import com.legosoft.cqrs.service.GrupoEmpresarialService;
import com.legosoft.cqrs.service.UsuarioService;
import com.netflix.discovery.converters.Auto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
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

    @Autowired
    private AgenteService agenteService;

    @Autowired
    private UsuarioService usuarioService;

    public CompletableFuture<String> createCommandGrupo(GrupoEmpresarial grupoEmpresarial){

        Set<Agente> lsAgente = new HashSet<>();

        grupoEmpresarial.getAgentes().forEach(a -> {
            Agente ag = agenteService.findAgenteByNombreAgente(a.getNombreAgente());
            lsAgente.add(ag);
        });

        grupoEmpresarial.setId(null);
        grupoEmpresarial.setAgentes(lsAgente);
        grupoEmpresarial.setUsuario(usuarioService.findUsuarioByNombre(grupoEmpresarial.getUsuario().getNombreUsuario()));

        grupoEmpresarial = saveGrupo(grupoEmpresarial);

        CreateGrupoCommand createGrupoCommand = new CreateGrupoCommand(grupoEmpresarial.getId(), grupoEmpresarial.getNombreGrupo(), grupoEmpresarial.getFechaCreacion(), grupoEmpresarial.getEstatus());
        //TODO: Llamar cola si se necesita producir algo
        return commandGateway.send(createGrupoCommand);
    }

    public GrupoEmpresarial saveGrupo(GrupoEmpresarial grupoEmpresarial){
        return grupoEmpresarialRepository.save(grupoEmpresarial);
    }
}
