package com.legosoft.agentes.RabbitMQReceiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.legosoft.agentes.dtos.UsuarioDto;
import com.legosoft.agentes.model.Agente;
import com.legosoft.agentes.service.AgenteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.SimpleDateFormat;

@Slf4j
public class Receiver {
    public static final String RECEIVE_METHOD_NAME = "receiveMessageUsuario";

    @Autowired
    private AgenteService agenteService;

    Gson gson = new GsonBuilder().create();
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void receiveMessageUsuario(String message) {
        UsuarioDto usuario = gson.fromJson(message, UsuarioDto.class);
        String nombreAgente = usuario.getAgentes().stream().findFirst().get().getNombreAgente();
        System.out.println("nombre del agente despues de recivir la cola de usuarios:::: " + nombreAgente);
        sendGrupo(usuario,  nombreAgente);
        Agente ag = new Agente();
        BeanUtils.copyProperties(usuario.getAgentes().stream().findFirst().get(), ag);
//        agenteService.updateCommandRelacionUsarioAgente(ag);
    }


    public void sendGrupo(UsuarioDto usuarioDto, String agente) {

        String nombreUsuario = usuarioDto.getNombreUsuario();
        String nombreGrupo = "Group-" +agente;
        log.info("Recibio la cola del usuario");

    agenteService.EnviaColaGrupo(nombreGrupo, nombreUsuario.replaceAll("\"", ""), agente);

    }


}
