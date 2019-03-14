package com.legosoft.agentes.service.impl;

import com.google.gson.Gson;
import com.legosoft.agentes.eventsourcing.command.agente.AsociarAgenteUsuarioCommand;
import com.legosoft.agentes.eventsourcing.command.agente.AsociarCompaniaGrupoCommand;
import com.legosoft.agentes.eventsourcing.command.agente.CreateCompaniaCommand;
import com.legosoft.agentes.model.Compania;
import com.legosoft.agentes.repository.CompaniaRepository;
import com.legosoft.agentes.service.CompaniaService;
import com.legosoft.agentes.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service("companiaService")
public class CompaniaServiceImpl implements CompaniaService {

    private  CommandGateway commandGateway ;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CompaniaRepository companiaRepository;

    public CompaniaServiceImpl(CommandGateway commandGateway, CompaniaRepository companiaRepository){
        this.commandGateway = commandGateway;
        this.companiaRepository = companiaRepository;
    }

    public Response createCompaniaCommand(Compania compania){

        Compania validaCompania = companiaRepository.findByNombreCompania(compania.getNombreCompania());
        Response response;

        if (validaCompania == null){
            compania.setIdCompania(null);
            compania = companiaRepository.save(compania);
            Long idEvent = compania.getIdCompania();
            CreateCompaniaCommand companiaCommand = new CreateCompaniaCommand(compania.getIdCompania(), compania);
            rabbitTemplate.convertAndSend("agente_usuario","agente_usuario", new Gson().toJson(compania));
            rabbitTemplate.convertAndSend("usuarioCQRS","usuarioCQRS", new Gson().toJson(compania));
            CompletableFuture<String> future = commandGateway.send(companiaCommand);
            response = new Response(HttpStatus.ACCEPTED.value(), "La compañia se creo correctamente");
        }else{
            response = new Response(HttpStatus.METHOD_NOT_ALLOWED.value(), "La compañia ya existe");
        }
        return response;
    }

    public CompletableFuture<String> relacionarUsuarioCompania(Compania compania){
        Compania ag = companiaRepository.findByNombreCompania(compania.getNombreCompania());
        System.out.println("nombre del agente despues de la consulta:: " + ag.getNombreCompania());
        ag.setEstatus("USUARIO_RELACIOANDO_COMPANIA");
        AsociarAgenteUsuarioCommand asociarAgenteUsuarioCommand = new AsociarAgenteUsuarioCommand(compania.getIdCompania(), compania);
        return commandGateway.sendAndWait(asociarAgenteUsuarioCommand);
    }

    public CompletableFuture<String> asociarCompaniaGrupo(Compania compania){
        Compania ag = companiaRepository.findByNombreCompania(compania.getNombreCompania());
        ag.setEstatus("COMPANIA_RELACIONADA_A_GRUPO");
        AsociarCompaniaGrupoCommand command = new AsociarCompaniaGrupoCommand(ag.getIdCompania(), ag);
        return commandGateway.sendAndWait(command);
    }


    public void EnviaColaGrupo(String nombreGrupo, String nombreUsuario, String nombreCompania){
        Map<String, Object> json = new HashMap<>();
        List<String> agenteList = new ArrayList<>();
        agenteList.add(nombreCompania);
        json.put("nombreUsuario", nombreUsuario);
        json.put("nombreGrupo", nombreGrupo);
        json.put("agenteList", agenteList);
        rabbitTemplate.convertAndSend("grupo_empresarial_events_test","grupo_empresarial_events_test", new Gson().toJson(json));
    }

    public Compania findCompaniaById(Long idCompania){
        return companiaRepository.findByIdCompania(idCompania);
    }

    public List<Compania> findAllCompanias(){
        return companiaRepository.findAll();
    }

    public Compania saveOrUpdate(Compania compania){
        return companiaRepository.save(compania);
    }

    public void deleteCompania(Compania compania){
        companiaRepository.delete(compania);
    }

    public Compania findCompaniaByNombre(String nombreCompania){
        return companiaRepository.findByNombreCompania(nombreCompania);
    }

}
