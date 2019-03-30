package com.legosoft.cqrs.service.impl;

import com.legosoft.cqrs.eventsourcing.command.grupoempresarial.CreateGrupoCommand;
import com.legosoft.cqrs.models.Compania;
import com.legosoft.cqrs.models.GrupoEmpresarial;
import com.legosoft.cqrs.repository.GrupoEmpresarialRepository;
import com.legosoft.cqrs.service.CompaniaService;
import com.legosoft.cqrs.service.GrupoEmpresarialService;
import com.legosoft.cqrs.service.UsuarioService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
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
    private CompaniaService companiaService;

    @Autowired
    private UsuarioService usuarioService;

    public CompletableFuture<String> createCommandGrupo(GrupoEmpresarial grupoEmpresarial){

        Set<Compania> lsCompania = new HashSet<>();
        GrupoEmpresarial gp = grupoEmpresarialRepository.findByNombreGrupo(grupoEmpresarial.getNombreGrupo());

        if (gp != null){
            grupoEmpresarial.setId(gp.getId());
        }else{
            grupoEmpresarial.setId(null);
        }

        grupoEmpresarial.getCompanias().forEach(a -> {
            Compania compania = companiaService.findCompaniaByNombre(a.getNombreCompania());
            lsCompania.add(compania);
        });


        grupoEmpresarial.setCompanias(lsCompania);
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
