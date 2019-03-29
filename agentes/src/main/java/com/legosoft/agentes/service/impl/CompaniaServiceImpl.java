package com.legosoft.agentes.service.impl;

import com.google.gson.Gson;
import com.legosoft.agentes.circuitbreaker.ConsumerRestCircuitBreaker;
import com.legosoft.agentes.dtos.CompaniaDto;
import com.legosoft.agentes.eventsourcing.command.agente.*;
import com.legosoft.agentes.model.Compania;
import com.legosoft.agentes.repository.CompaniaRepository;
import com.legosoft.agentes.service.CompaniaService;
import com.legosoft.agentes.utils.Response;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import sun.rmi.runtime.Log;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@Service("companiaService")
public class CompaniaServiceImpl implements CompaniaService {

    private final String URL_SERVICE = "http://192.168.20.93:8081/message/restore/create/";

    private Compania compRest;

    @LoadBalanced
    private RestTemplate restTemplate = new RestTemplate();

    private  CommandGateway commandGateway ;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CompaniaRepository companiaRepository;

    @Autowired
    private ConsumerRestCircuitBreaker consumerRestCircuitBreaker;

    public CompaniaServiceImpl(CommandGateway commandGateway, CompaniaRepository companiaRepository){
        this.commandGateway = commandGateway;
        this.companiaRepository = companiaRepository;
    }

    @Transactional
    public Response createCompaniaCommand(Compania compania){

        Map<String, Object> info = new HashMap<>();
        Map<String, Object> infoCqrs = new HashMap<>();

        Compania validaCompania = companiaRepository.findByNombreCompania(compania.getNombreCompania());
        Response response;

        if (validaCompania == null){
            compania.setIdCompania(null);
            Compania comp = companiaRepository.save(compania);

            CreateCompaniaCommand companiaCommand = new CreateCompaniaCommand(comp.getIdCompania(), comp);
            comp.setFechaCreacionMillis(comp.getFechaCreacion().getTime());
            comp.setFechaCreacion(null);


            try{
//                    rabbitTemplate.convertAndSend("agente_usuario", "agente_usuario", new Gson().toJson(comp));
            }catch (Exception e){

                log.info("Entro al catch 1");
                info.put("routingKey", "agente_usuario");
                info.put("exchange", "agente_usuario");
                info.put("body", compania);

                log.info("<<<< Enviando al exchange:: " + info.get("exchange") + " >>>>");
                log.info("<<<< Enviando al routingKey:: " + info.get("routingKey") + " >>>>");

                    consumerRestCircuitBreaker.consumerRestRabbitService(info);
            }

            try{

//                    rabbitTemplate.convertAndSend("usuarioCQRS", "usuarioCQRS", new Gson().toJson(comp));
            }catch (Exception e){

                log.info("Entro al catch 2");
                infoCqrs.put("routingKey", "usuarioCQRS");
                infoCqrs.put("exchange", "usuarioCQRS");
                infoCqrs.put("body", compania);

                    log.info("<<<< Enviando al exchange:: " + infoCqrs.get("exchange") + " >>>>");
                    log.info("<<<< Enviando al routingKey:: " + infoCqrs.get("routingKey") + " >>>>");

                    consumerRestCircuitBreaker.consumerRestRabbitService(infoCqrs);
            }

            CompletableFuture<String> future = commandGateway.send(companiaCommand);
            response = new Response(HttpStatus.ACCEPTED.value(), "La compañia se creo correctamente", compania);
        }else{
            response = new Response(HttpStatus.METHOD_NOT_ALLOWED.value(), "La compañia ya existe", compania);
        }
        return response;
    }


    public void relacionarUsuarioCompania(Compania compania){
        log.info("<<<< Esta en el servicio que relaciona el usuario con la compañia >>>>>>>>>>>>>>>>>>>>>");
        Compania ag = companiaRepository.findByNombreCompania(compania.getNombreCompania());
        ag.setEstatus("USUARIO_RELACIONADO_COMPANIA");
        AsociarAgenteUsuarioCommand asociarAgenteUsuarioCommand = new AsociarAgenteUsuarioCommand(compania.getIdCompania(), ag);
        CompletableFuture<String> future = commandGateway.send(asociarAgenteUsuarioCommand);

    }


    public CompletableFuture<String> errorRelationUsuarioCompaniaCommand(Compania compania){
        log.info("<<<<<< esta en el servicio Generando evento de error al relacionar el usuario con la compañia >>>>>>>>>>");
        compania.setEstatus("USUARIO_NO RELACIONADO_A_COMPANIA");
        ErrorRelationUsuarioCompaniaCommand command = new ErrorRelationUsuarioCompaniaCommand(compania.getIdCompania(), compania);
        CompletableFuture<String> future = commandGateway.send(command);
//        errorRelationGrupoCompaniaCommand(compania);
        return future;
    }

    public CompletableFuture<String> asociarCompaniaGrupo(Compania compania){

        log.info("<<<<<<esta en el servicio Generando evento de asociar grupo con la compañia >>>>>>>>>>");
        Compania ag = companiaRepository.findByNombreCompania(compania.getNombreCompania());
        ag.setEstatus("COMPANIA_RELACIONADA_A_GRUPO");
        AsociarCompaniaGrupoCommand command = new AsociarCompaniaGrupoCommand(compania.getIdCompania(), ag);
        return commandGateway.send(command);
    }


    public CompletableFuture<String> errorRelationGrupoCompaniaCommand(Compania compania){
        log.info("<<<<<<esta en el servicio Generando evento de error al relacionar el grupo con la compañia >>>>>>>>>>");
        ErrorRelationGrupoCompaniaCommand command = new ErrorRelationGrupoCompaniaCommand(compania.getIdCompania(), compania);
        return commandGateway.send(command);
    }


    public void enviaColaGrupo(String nombreGrupo, String nombreUsuario, String nombreCompania){

        Map<String, Object> json = new HashMap<>();
        Map<String, Object> requestMessageRestore = new HashMap<>();

        List<String> agenteList = new ArrayList<>();
        agenteList.add(nombreCompania);
        json.put("nombreUsuario", nombreUsuario);
        json.put("nombreGrupo", nombreGrupo);
        json.put("agenteList", agenteList);

        try {
            rabbitTemplate.convertAndSend("grupo_empresarial_events_test","grupo_empresarial_events_test", new Gson().toJson(json));
        }catch (Exception e){

            requestMessageRestore.put("routingKey", "usuarioCQRS");
            requestMessageRestore.put("exchange", "usuarioCQRS");
            requestMessageRestore.put("body", json);

            consumerRestCircuitBreaker.consumerRestRabbitService(requestMessageRestore);
        }

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
