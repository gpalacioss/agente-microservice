package com.legosoft.facultades.services.impl;

import com.google.gson.Gson;
import com.legosoft.facultades.circuitbreaker.ConsumerRestCircuitBreaker;
import com.legosoft.facultades.commands.perfil.CreatePerfilCommand;
import com.legosoft.facultades.commands.perfil.DisablePerfilCommand;
import com.legosoft.facultades.commands.perfil.UpdatePerfilCommand;
import com.legosoft.facultades.models.Perfil;
import com.legosoft.facultades.models.Permiso;
import com.legosoft.facultades.models.Rol;
import com.legosoft.facultades.repository.PerfilRepository;
import com.legosoft.facultades.repository.RolRepository;
import com.legosoft.facultades.services.PerfilService;
import com.legosoft.facultades.utils.Response;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service("perfilService")
public class PerfilServiceImpl implements PerfilService {

    private static final String TIPO = "perfil";

    private CommandGateway commandGateway;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private ConsumerRestCircuitBreaker consumerRestCircuitBreaker;

    public PerfilServiceImpl(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

    @Override
    public ResponseEntity savePefil(Perfil perfil) {

        Map<String, Object> info = new HashMap<>();

        Perfil per = perfilRepository.findByNombre(perfil.getNombre());

        if (Objects.isNull(per)){

            Set<Rol> roles = new HashSet<>();
            perfil.setIdPerfil(null);

            perfil.getRolesPerfil().stream().forEach(r ->
                    roles.add(rolRepository.findById(r.getIdRol()).get())
            );

            perfil.setRolesPerfil(roles);

            Perfil p = perfilRepository.save(perfil);

            perfil.setTipo(TIPO);

            CreatePerfilCommand command = new CreatePerfilCommand(
                    p.getIdPerfil(),p.getNombre(),
                    p.getActivo(),p.getIsAdmin(),
                    p.getRolesPerfil()
            );

            try {
                rabbitTemplate.convertAndSend("facultades","*", new Gson().toJson(command));
            }catch (Exception e){

                info.put("routingKey", "usuarioCQRS");
                info.put("exchange", "usuarioCQRS");
                info.put("body", command);
                consumerRestCircuitBreaker.consumerRestRabbitService(info);

            }

            try {
                rabbitTemplate.convertAndSend("ExchangeCQRS","*", new Gson().toJson(perfil));
            }catch (Exception e){

                info.put("routingKey", "usuarioCQRS");
                info.put("exchange", "usuarioCQRS");
                info.put("body", command);
                consumerRestCircuitBreaker.consumerRestRabbitService(info);

            }

            commandGateway.send(command);
            return new ResponseEntity(perfil, HttpStatus.OK);
        }else{

            return new ResponseEntity(new Response(HttpStatus.BAD_REQUEST.value(),"El rol " + perfil.getNombre() + " Ya existe"), HttpStatus.BAD_REQUEST);

        }

    }

    @Override
    public CompletableFuture<String> updatePerfil(Perfil perfil) {

        Perfil p = perfilRepository.findById(perfil.getIdPerfil()).get();

        UpdatePerfilCommand command = new UpdatePerfilCommand(
                p.getIdPerfil(),perfil.getNombre(),
                perfil.getActivo(),perfil.getIsAdmin()
        );

        return commandGateway.send(command);
    }

    @Override
    public CompletableFuture<String> deshabilitarPerfil(Perfil perfil) {

        Perfil p = perfilRepository.findById(perfil.getIdPerfil()).get();

        DisablePerfilCommand command = new DisablePerfilCommand(p.getIdPerfil());

        return commandGateway.send(command);

    }

    @Override
    public void findPerfilById(Long perfilId) {

    }

    @Override
    public void findAll() {

    }
}
