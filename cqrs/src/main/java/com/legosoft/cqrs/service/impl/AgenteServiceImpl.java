package com.legosoft.cqrs.service.impl;

import com.legosoft.cqrs.eventsourcing.command.agente.CreateAgenteCommand;
import com.legosoft.cqrs.models.Agente;
import com.legosoft.cqrs.repository.AgenteRepository;
import com.legosoft.cqrs.service.AgenteService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service("agenteService")
public class AgenteServiceImpl implements AgenteService {

    private CommandGateway commandGateway ;

    public AgenteServiceImpl(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

//    @Autowired
//    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AgenteRepository agenteRepository;

    public Agente createCommandAgente(Agente agente){
        agente.setIdAgente(null);
        Agente newAgente =  saveAgente(agente);
        CreateAgenteCommand agenteCommand = new CreateAgenteCommand(newAgente.getIdAgente(), newAgente.getNombreAgente(), newAgente.getFechaCracion(), newAgente.isActivo());
//        rabbitTemplate.convertAndSend("agente_usuario","agente_usuario", new Gson().toJson(new MessageColas("Agente Creado para el alfons", " gpalacios@legosoft.com.mx", agente)));
        commandGateway.send(agenteCommand);
        return newAgente;
    }

    public Agente saveAgente(Agente agente){
        return agenteRepository.save(agente);
    }

    public Agente findAgenteByNombreAgente(String nombreAgente){
        return agenteRepository.findByNombreAgente(nombreAgente);
    }
}
