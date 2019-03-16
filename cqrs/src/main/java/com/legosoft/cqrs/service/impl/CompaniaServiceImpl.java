package com.legosoft.cqrs.service.impl;

import com.legosoft.cqrs.eventsourcing.command.agente.CreateAgenteCommand;
import com.legosoft.cqrs.models.Compania;
import com.legosoft.cqrs.repository.CompaniaRepository;
import com.legosoft.cqrs.service.CompaniaService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("companiaService")
public class CompaniaServiceImpl implements CompaniaService {

    private CommandGateway commandGateway ;

    public CompaniaServiceImpl(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

//    @Autowired
//    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CompaniaRepository companiaRepository;

    public Compania createCommandCompania(Compania compania){
        compania.setIdCompania(null);
        Compania comp =  saveCompania(compania);
        CreateAgenteCommand agenteCommand = new CreateAgenteCommand(comp.getIdCompania(), comp.getNombreCompania(), comp.getFechaCreacion(), comp.isActivo());
//        rabbitTemplate.convertAndSend("agente_usuario","agente_usuario", new Gson().toJson(new MessageColas("Agente Creado para el alfons", " gpalacios@legosoft.com.mx", agente)));
        commandGateway.send(agenteCommand);
        return comp;
    }

    public Compania saveCompania(Compania compania){
        return companiaRepository.save(compania);
    }

    public Compania findCompaniaByNombre(String nombreCompania){
        return companiaRepository.findByNombreCompania(nombreCompania);
    }
}
