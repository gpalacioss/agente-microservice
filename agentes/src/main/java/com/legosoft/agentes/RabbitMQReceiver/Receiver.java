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
import org.springframework.transaction.annotation.Propagation;


import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class Receiver {
    public static final String RECEIVE_METHOD_NAME = "receiveMessageUsuario";

    @Autowired
    private CompaniaService companiaService;

    Gson gson = new GsonBuilder().create();
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RabbitListener(queues = {"usuario_nuevo"})
    public void receiveMessageUsuario(String message) {
        log.info(message);

        UsuarioDto usuario = gson.fromJson(message, UsuarioDto.class);
        Compania comp = usuario.getCompanias().stream().findFirst().get();

        Compania ag = companiaService.findCompaniaByNombre(usuario.getCompanias().stream().findFirst().get().getNombreCompania());
        if ("ERROR_AL_CREAR_USUARIO".equals(usuario.getEstatus())){
            companiaService.errorRelationUsuarioCompaniaCommand(ag);
//            companiaService.errorRelationGrupoCompaniaCommand(ag);
        }else{
            companiaService.relacionarUsuarioCompania(ag);
            sendGrupo(usuario,  comp.getNombreCompania(), comp.getNombreGrupo());
        }



    }


    public void sendGrupo(UsuarioDto usuarioDto, String nombreCompania, String nombreGrupo) {

        String nombreUsuario = usuarioDto.getNombreUsuario();
        log.info("Recibio mensaje del micreservicio de usuario y se la envia al microservicio de grupo empresarial ");

        companiaService.enviaColaGrupo(nombreGrupo, nombreUsuario, nombreCompania);

    }


}
