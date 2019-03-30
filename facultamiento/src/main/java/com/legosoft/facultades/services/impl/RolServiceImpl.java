package com.legosoft.facultades.services.impl;

import com.google.gson.Gson;
import com.legosoft.facultades.commands.rol.CreateRolCommand;
import com.legosoft.facultades.commands.rol.DisableRolCommand;
import com.legosoft.facultades.commands.rol.UpdateRolCommand;
import com.legosoft.facultades.models.Permiso;
import com.legosoft.facultades.models.Rol;
import com.legosoft.facultades.repository.PermisoRepository;
import com.legosoft.facultades.repository.RolRepository;
import com.legosoft.facultades.services.RolService;
import com.legosoft.facultades.utils.Response;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service("rolService")
public class RolServiceImpl implements RolService {

    private static final String TIPO = "rol";

    private CommandGateway commandGateway;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    public RolServiceImpl(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

    @Override
    public ResponseEntity saveRol(Rol rol) {

        Set<Permiso> permisos = new HashSet<>();
        String message = "";

        Rol r = rolRepository.findByNombre(rol.getNombre());

        if (r == null){

            rol.setIdRol(null);
            message = "El rol fue actualizado correctamente";

            rol.getPermisos().stream().forEach(p -> {
                permisos.add(permisoRepository.findById(p.getIdPermiso()).get());
            });

            rol.setPermisos(permisos);
            rol.setTipo(TIPO);


            rol = rolRepository.save(rol);

            CreateRolCommand command = new CreateRolCommand(rol.getIdRol(),rol.getNombre(),rol.getActivo(),rol.getPermisos());

            rabbitTemplate.convertAndSend("ExchangeCQRS","*", new Gson().toJson(rol));

            commandGateway.send(command);

            return new ResponseEntity(rol, HttpStatus.OK);
        }else{

            return new ResponseEntity(new Response(HttpStatus.BAD_REQUEST.value(),"El rol " + rol.getNombre() + " Ya existe"), HttpStatus.BAD_REQUEST);

        }

    }

    @Transactional
    @Override
    public CompletableFuture<String> updateRol(Rol rol) {

        Rol r = rolRepository.findById(rol.getIdRol()).get();

        rolRepository.save(rol);

        UpdateRolCommand command = new UpdateRolCommand(r.getIdRol(),rol.getNombre(),rol.getActivo());

        return commandGateway.send(command);

    }

    @Override
    public CompletableFuture<String> deshabilitarRol(Rol rol) {

        Rol r = rolRepository.findById(rol.getIdRol()).get();

        rol.setActivo(false);

        rolRepository.save(r);

        DisableRolCommand command = new DisableRolCommand(r.getIdRol());

        return commandGateway.send(command);

    }

    @Override
    public void findRoleById(Long roleId) {


    }

    @Override
    public void findAll() {


    }

}
