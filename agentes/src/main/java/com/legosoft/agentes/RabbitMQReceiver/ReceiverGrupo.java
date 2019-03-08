package com.legosoft.agentes.RabbitMQReceiver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceiverGrupo {

    public static final String RECEIVE_METHOD_NAME_GRUPO = "receiveMessageGrupo";

    public void receiveMessageGrupo(String messageGrupo) {
        log.info("El proceso esta terminado y completo");
    }
}
