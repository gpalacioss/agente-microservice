package com.legosoft.cqrs.RabbitMQreceiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.legosoft.cqrs.enums.GrupoEmpresarialEstatus;
import com.legosoft.cqrs.models.*;
import com.legosoft.cqrs.service.*;
import com.netflix.discovery.converters.Auto;
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

    @Autowired
    private PermisoService permisoService;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private RolService rolService;

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
//                GrupoEmpresarial grupo = generaEmpresa(mensaje);
                GrupoEmpresarial grupoEmpresarial = gson.fromJson(mensaje, GrupoEmpresarial.class);
                grupoEmpresarialService.createCommandGrupo(grupoEmpresarial);
                break;
            case "Usuario":
//                Usuario usuario = generaUsuario(mensaje);
                Usuario usuario = gson.fromJson(mensaje, Usuario.class);
                System.out.println(usuario.getNombreCompleto());
                usuarioService.createCommandUsuario(usuario);
                break;
            case "agente":
                Agente agente = gson.fromJson(mensaje, Agente.class);
                agenteService.createCommandAgente(agente);
                break;
            case "permiso":
                Permiso permiso = gson.fromJson(mensaje, Permiso.class);
                permisoService.createCommandPermiso(permiso);
                break;
            case "perfil":
                Perfil perfil = gson.fromJson(mensaje, Perfil.class);
                perfilService.createCommandPerfil(perfil);
                break;
            case "rol":
                Rol rol = gson.fromJson(mensaje, Rol.class);
                rolService.createCommandRol(rol);
                break;
        }
    }

    private GrupoEmpresarial generaEmpresa(String mensaje){

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(mensaje);

        String nombreUsuario = String.valueOf(jsonObject.get("nombreUsuario"));
        Long id = Long.valueOf(String.valueOf(jsonObject.get("id")));
        String nombreGrupo = String.valueOf(jsonObject.get("nombreGrupo"));
        Date fecha = null;
        try {
            fecha = dt.parse(String.valueOf(jsonObject.get("fechaCreacion")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String estatus =String.valueOf(jsonObject.get("estatus"));

        GrupoEmpresarial grupo =  new GrupoEmpresarial();
        grupo.setId(id);
        grupo.setNombreGrupo(nombreGrupo.replaceAll("\"", ""));
        grupo.setFechaCreacion(fecha);
        grupo.setEstatus(estatus.equals("PENDIENTE") ? GrupoEmpresarialEstatus.PENDIENTE : GrupoEmpresarialEstatus.COMPLETO);
        grupo.setUsuario(usuarioService.findUsuarioByNombre(nombreUsuario.replaceAll("\"", "")));

        return grupo;
    }

    private Usuario generaUsuario(String mensaje){

        Usuario usuario = gson.fromJson(mensaje, Usuario.class);
        System.out.println(usuario.getAgentes().stream().findFirst().get().getNombreAgente());
        //TODO: esto se hace por que la cola de agente a cqrs o esta funcionando y se tiene que guardar aqui cuando envien el agente del servico de usuario, cuando funcione la cola eliminar y hacer solo la busqueda por nombre
//        Agente ag = agenteService.createCommandAgente(usuario.getAgentes().stream().findFirst().get());
//        usuario.setNombreUsuario(usuario.getNombreUsuario().replaceAll("\"", ""));
        usuario.getAgentes().add(agenteService.findAgenteByNombreAgente(usuario.getAgentes().stream().findFirst().get().getNombreAgente()));
        return usuario;

    }

}
