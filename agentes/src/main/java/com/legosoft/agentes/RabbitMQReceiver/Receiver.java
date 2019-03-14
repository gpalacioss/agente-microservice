package com.legosoft.agentes.RabbitMQReceiver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.legosoft.agentes.dtos.CompaniaDto;
import com.legosoft.agentes.dtos.UsuarioDto;
import com.legosoft.agentes.model.Compania;
import com.legosoft.agentes.service.CompaniaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;

@Slf4j
@Component
public class Receiver {
    public static final String RECEIVE_METHOD_NAME = "receiveMessageUsuario";

    @Autowired
    private CompaniaService companiaService;

    Gson gson = new GsonBuilder().create();
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Transactional
    @RabbitListener(queues = {"usuario_nuevo"})
    public void receiveMessageUsuario(String message) {
        log.info(message);
        UsuarioDto usuario = gson.fromJson(message, UsuarioDto.class);
        CompaniaDto comp = usuario.getCompanias().stream().findFirst().get();
//        String nombreAgente = usuario.getCompanias().stream().findFirst().get().getNombreCompania();
        log.info("Nombre del usuario ::" + usuario.getNombreUsuario());
        log.info("Nombre de la compania:: " + comp.getNombreCompania());
        log.info("Nombre del grupo:: " + comp.getNombreGrupo());
        sendGrupo(usuario,  comp.getNombreCompania(), comp.getNombreGrupo());
        Compania ag = companiaService.findCompaniaByNombre(usuario.getCompanias().stream().findFirst().get().getNombreCompania());
        companiaService.relacionarUsuarioCompania(ag);
    }


    public void sendGrupo(UsuarioDto usuarioDto, String nombreCompania, String nombreGrupo) {

        String nombreUsuario = usuarioDto.getNombreUsuario();
        log.info("Recibio mensaje del micreservicio de usuario y se la envia al microservicio de grupo empresarial ");

        companiaService.EnviaColaGrupo(nombreGrupo, nombreUsuario, nombreCompania);

    }


}
