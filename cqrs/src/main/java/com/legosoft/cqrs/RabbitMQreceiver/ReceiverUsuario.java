package com.legosoft.cqrs.RabbitMQreceiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.legosoft.cqrs.enums.GrupoEmpresarialEstatus;
import com.legosoft.cqrs.models.Agente;
import com.legosoft.cqrs.models.GrupoEmpresarial;
import com.legosoft.cqrs.models.Usuario;
import com.legosoft.cqrs.service.AgenteService;
import com.legosoft.cqrs.service.GrupoEmpresarialService;
import com.legosoft.cqrs.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiverUsuario {

    public static final String RECEIVE_METHOD_NAME = "receiveMessageUsuario";
    private ObjectMapper objectMapper;

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


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

        switch (tipo){
            case "grupo":
                GrupoEmpresarial grupo = generaEmpresa(mensaje);
                grupoEmpresarialService.createCommandGrupo(grupo);
                break;
            case "Usuario":
                Usuario usuario = generaUsuario(mensaje);
                System.out.println(usuario.getNombreCompleto());
                usuarioService.createCommandUsuario(usuario);
                break;
            case "agente":
                Agente agente = gson.fromJson(mensaje, Agente.class);
                agenteService.createCommandAgente(agente);
                break;
        }
    }

    private GrupoEmpresarial generaEmpresa(String mensaje){

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(mensaje);

        String nombreUsuario = String.valueOf(jsonObject.get("nombreUsuario"));
        String idEvent = String.valueOf(jsonObject.get("id"));
        String nombreGrupo = String.valueOf(jsonObject.get("nombreGrupo"));
        Date fecha = null;
        try {
            fecha = dt.parse(String.valueOf(jsonObject.get("fechaCreacion")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String estatus =String.valueOf(jsonObject.get("estatus"));

        GrupoEmpresarial grupo =  new GrupoEmpresarial();
        grupo.setIdEvent(idEvent);
        grupo.setNombreGrupo(nombreGrupo);
        grupo.setFechaCreacion(fecha);
        grupo.setEstatus(estatus.equals("PENDIENTE") ? GrupoEmpresarialEstatus.PENDIENTE : GrupoEmpresarialEstatus.COMPLETO);
        grupo.setUsuario(usuarioService.findUsuarioByNombre(nombreUsuario.replaceAll("\"", "")));

        return grupo;
    }

    private Usuario generaUsuario(String mensaje){

        Usuario usuario = new Usuario();

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(mensaje);

        Long id = Long.valueOf(String.valueOf(jsonObject.get("id")));
        String idEvent = String.valueOf(jsonObject.get("idEvent"));
        String nombreUsuario = String.valueOf(jsonObject.get("idEvent"));
        String nombreCompleto = String.valueOf(jsonObject.get("nombreCompleto"));
        String email = String.valueOf(jsonObject.get("email"));
        String password = String.valueOf(jsonObject.get("password"));
        boolean administrador = Boolean.parseBoolean(String.valueOf(jsonObject.get("administrador")));
        boolean activo = Boolean.parseBoolean(String.valueOf(jsonObject.get("activo")));

        String nombreAgente = String.valueOf(jsonObject.get("nombreAgente"));

        usuario.setNombreUsuario(nombreUsuario);
        usuario.setNombreCompleto(nombreCompleto);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setAdministrador(administrador);
        usuario.setActivo(activo);
        usuario.getAgentes().add(agenteService.findAgenteByNombreAgente(nombreAgente));
        return usuario;
    }

}
