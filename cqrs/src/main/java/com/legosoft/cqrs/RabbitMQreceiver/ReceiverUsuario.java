package com.legosoft.cqrs.RabbitMQreceiver;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.legosoft.cqrs.models.*;
import com.legosoft.cqrs.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.text.SimpleDateFormat;

@Slf4j
public class ReceiverUsuario {

    public static final String RECEIVE_METHOD_NAME = "receiveMessageUsuario";

    Gson gson = new GsonBuilder().create();
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Autowired
    private CompaniaService companiaService;

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
                GrupoEmpresarial grupoEmpresarial = gson.fromJson(mensaje, GrupoEmpresarial.class);
                grupoEmpresarialService.createCommandGrupo(grupoEmpresarial);
                break;
            case "Usuario":
                Usuario usuario = gson.fromJson(mensaje, Usuario.class);
                System.out.println(usuario.getNombreCompleto());
                usuarioService.createCommandUsuario(usuario);
                break;
            case "compania":

                Compania compania = gson.fromJson(mensaje, Compania.class);
                companiaService.createCommandCompania(compania);
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


}
