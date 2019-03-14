package com.legosoft.agentes.service;

import com.legosoft.agentes.model.Compania;
import com.legosoft.agentes.utils.Response;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CompaniaService {

    public Response createCompaniaCommand(Compania compania);


    /**
     * Servicio que genera el comando de asociar usuario administrador a la compañia
     * @param compania
     * @return
     */
    public CompletableFuture<String> relacionarUsuarioCompania(Compania compania);

    /**
     * Servicio que genera un comando para guardar el evento de asociacion de compañia a un grupo empresarial
     * @param compania
     * @return
     */
    public CompletableFuture<String> asociarCompaniaGrupo(Compania compania);

    /**
     *
     * Servicio que consulta a un agente por su id
     * @param idCompania
     * @return
     */
    public Compania findCompaniaById(Long idCompania);

    /**
     * Servicio que consulta todos los agetes
     * @return
     */
    public List<Compania> findAllCompanias();

    /**
     * Servicio que guarda o actualiza la informacion de un agente
     * @param compania
     * @return
     */
    public Compania saveOrUpdate(Compania compania);

    /**
     * Servicio que elimina el agente
     * @param compania
     */
    public void deleteCompania(Compania compania);

    /**
     * Servicio que busca un agente por nombre
     */
    public Compania findCompaniaByNombre(String nombreCompania);

    public void EnviaColaGrupo(String nombreGrupo, String nombreUsuario, String nombreCompania);

}
