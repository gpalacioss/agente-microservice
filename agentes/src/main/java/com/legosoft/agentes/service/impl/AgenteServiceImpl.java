package com.legosoft.agentes.service.impl;

import com.google.gson.Gson;
import com.legosoft.agentes.dtos.MessageColas;
import com.legosoft.agentes.eventsourcing.command.agente.CreateAgenteCommand;
import com.legosoft.agentes.model.Agente;
import com.legosoft.agentes.repository.AgenteRepository;
import com.legosoft.agentes.service.AgenteService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service("agenteService")
public class AgenteServiceImpl implements AgenteService {

    private  CommandGateway commandGateway ;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AgenteRepository agenteRepository;

    public AgenteServiceImpl(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }


    public CompletableFuture<String> createCommandAgente(Agente agente){
        String id = UUID.randomUUID().toString();
        agente.setIdAgenteEvent(id);
        CreateAgenteCommand agenteCommand = new CreateAgenteCommand(agente.getIdAgenteEvent(), agente.getNombreAgente(), agente.getFechaCreacion(), agente.isActivo());

        rabbitTemplate.convertAndSend("agente_usuario","agente_usuario", new Gson().toJson(agente));
        return commandGateway.send(agenteCommand);
    }


    public Agente findAgenteById(Long idAgente){
        return agenteRepository.findByIdAgente(idAgente);
    }

    public List<Agente> findAllAgentes(){
        return agenteRepository.findAll();
    }

    public Agente saveOrUpdate(Agente agente){
        return agenteRepository.save(agente);
    }

    public void deleteAgente(Agente agente){
        agenteRepository.delete(agente);
    }

}
