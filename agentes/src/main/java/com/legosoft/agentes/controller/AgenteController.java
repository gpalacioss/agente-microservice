package com.legosoft.agentes.controller;

import com.legosoft.agentes.dtos.AgenteDto;
import com.legosoft.agentes.model.Agente;
import com.legosoft.agentes.service.AgenteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/agente")
public class AgenteController {

    @Autowired
    private AgenteService agenteService;


    @GetMapping("/getAgentes")
    public List<Agente> getAllAgentes(){
        return agenteService.findAllAgentes();
    }

    @GetMapping("/gerAgente/{idAgente}")
    public Agente getAgenteById(@PathVariable("idAgente") Long idAgente){
        return agenteService.findAgenteById(idAgente);
    }

    @PostMapping("/saveAgenteCommand")
    public void createCommandAgente(@RequestBody AgenteDto agente){
        Agente ag = new Agente();
        BeanUtils.copyProperties(agente, ag);
        ag.setFechaCreacion(new Date());
        ag.setEstatus("PENDIENTE_ASIGNAR_USUARIO");
        agenteService.createCommandAgente(ag);
    }

    @PostMapping("/updateAgenteCommand")
    public void updateCommandAgente(@RequestBody AgenteDto agente){
        Agente ag = new Agente();
        BeanUtils.copyProperties(agente, ag);
        agenteService.updateCommandRelacionUsarioAgente(ag);
    }

    @PostMapping("/saveAgente")
    public Agente saveAgente(@RequestBody Agente agente){
        return agenteService.saveOrUpdate(agente);
    }

    @DeleteMapping("/EliminaAgente")
    public void deleteAgente(@RequestBody Agente agente){
        Agente a = agenteService.findAgenteById(agente.getIdAgente());
        if (a != null){
            agenteService.deleteAgente(a);
        }
    }

}
