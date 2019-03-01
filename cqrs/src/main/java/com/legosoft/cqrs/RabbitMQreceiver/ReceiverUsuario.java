package com.legosoft.cqrs.RabbitMQreceiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.legosoft.cqrs.models.Agente;
import com.legosoft.cqrs.models.GrupoEmpresarial;
import com.legosoft.cqrs.models.Usuario;
import com.legosoft.cqrs.service.AgenteService;
import com.legosoft.cqrs.service.GrupoEmpresarialService;
import com.legosoft.cqrs.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;

public class ReceiverUsuario {

    public static final String RECEIVE_METHOD_NAME = "receiveMessageUsuario";
    private ObjectMapper objectMapper;

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

    @Autowired
    private AgenteService agenteService;

    @Autowired
    private GrupoEmpresarialService grupoEmpresarialService;

    @Autowired
    private UsuarioService usuarioService;

    public void receiveMessageUsuario(String message) {


        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(message);
        String tipo = String.valueOf(jsonObject.get("tipo"));
        System.out.println(tipo);
        System.out.println("[Receiver] ha recibido el mensaje \"" + message + '"');
        try {
            saveEvent(tipo.replaceAll("\"", ""), message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveEvent(String tipo, String mensaje) throws IOException {
        objectMapper = new ObjectMapper();

        switch (tipo){
            case "grupo":
                GrupoEmpresarial grupo =  gson.fromJson(mensaje, GrupoEmpresarial.class);
                grupoEmpresarialService.createCommandGrupo(grupo);
                break;
            case "Usuario":
                Usuario usuario = gson.fromJson(mensaje, Usuario.class);
                System.out.println(usuario.getNombreCompleto());
                usuarioService.createCommandUsuario(usuario);
                break;
            case "agente":
                Agente agente = gson.fromJson(mensaje, Agente.class);
                agenteService.createCommandAgente(agente);
                break;
        }
    }

}
