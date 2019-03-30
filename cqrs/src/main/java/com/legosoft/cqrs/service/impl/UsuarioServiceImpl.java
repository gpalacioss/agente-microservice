package com.legosoft.cqrs.service.impl;

import com.legosoft.cqrs.eventsourcing.command.usuario.CreateUsuarioCommand;
import com.legosoft.cqrs.models.Compania;
import com.legosoft.cqrs.models.Perfil;
import com.legosoft.cqrs.models.Usuario;
import com.legosoft.cqrs.repository.UsuarioRepository;
import com.legosoft.cqrs.service.CompaniaService;
import com.legosoft.cqrs.service.PerfilService;
import com.legosoft.cqrs.service.UsuarioService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

    private CommandGateway commandGateway ;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CompaniaService companiaService;

    @Autowired
    private PerfilService perfilService;

    public UsuarioServiceImpl(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }


    public CompletableFuture<String> createCommandUsuario(Usuario usuario){
        Set<Compania> lsCompanias = new HashSet<>();
        Set<Perfil> lstPerfiles = new HashSet<>();

        Usuario usr = usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario());

        if (usr != null){
            usuario.setId(usr.getId());
        }else{
            usuario.setId(null);
        }

        usuario.getCompanias().forEach(a -> {
            Compania comp = companiaService.findCompaniaByNombre(a.getNombreCompania());
            lsCompanias.add(comp);
        });

        if (usuario.getPerfiles().size() != 0){

            usuario.getPerfiles().forEach(p -> {
                Perfil pr = perfilService.finsPerfilByNombre(p.getNombre());
                lstPerfiles.add(pr);
            });

            usuario.setPerfiles(lstPerfiles);
        }



        usuario.setCompanias(lsCompanias);


        Usuario nvoUsuario = saveUsuario(usuario);

        CreateUsuarioCommand createUsuarioCommand = new CreateUsuarioCommand(nvoUsuario.getId(), nvoUsuario.getNombreUsuario(), nvoUsuario.getNombreCompleto(), nvoUsuario.getEmail(), nvoUsuario.getPassword(), nvoUsuario.isAdministrador(), nvoUsuario.isActivo());
        //TODO: Llamar cola si se necesita producir algo
        return commandGateway.send(createUsuarioCommand);
    }

    public Usuario saveUsuario(Usuario usuario){
        return  usuarioRepository.save(usuario);
    }

    public Usuario findUsuarioByNombre(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }
}
