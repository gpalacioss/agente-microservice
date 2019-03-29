package com.legosoft.agentes.RabbitMQReceiver;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.legosoft.agentes.model.Compania;
import com.legosoft.agentes.service.CompaniaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;


import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Component
public class ReceiverGrupo {

    public static final String RECEIVE_METHOD_NAME_GRUPO = "receiveMessageGrupo";

    @Autowired
    private CompaniaService companiaService;

    @Transactional
    @RabbitListener(queues = {"grupo_agente"})
    public void receiveMessageGrupo(String messageGrupo) {

        log.info("Mensaje recibido del microservicio grupo empresarial:: " + messageGrupo);

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(messageGrupo);
        String nombreCompania = String.valueOf(jsonObject.get("nombreCompania")).replaceAll("\"","").replaceAll("\\u005B", "").replaceAll("\\u005D", "");
        String estatus = String.valueOf(jsonObject.get("estatus"));

        Compania compania = companiaService.findCompaniaByNombre(nombreCompania);
        if ("FAILED_CREATE_GRUPO_EMPRESARIAL".equals(estatus)){
//            companiaService.errorRelationGrupoCompaniaCommand(compania);
        }else{
            companiaService.asociarCompaniaGrupo(compania);
        }

    }
}
