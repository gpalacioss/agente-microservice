package com.legosoft.cqrs.controller;

import com.legosoft.cqrs.dtos.AgenteDto;
import com.legosoft.cqrs.models.Agente;
import com.legosoft.cqrs.service.AgenteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/agente")
public class AgenteController {

    @Autowired
    private AgenteService agenteService;

    @PostMapping("/createAgente")
    public void createAgente(@RequestBody AgenteDto agenteDto){
        Agente ag = new Agente();
        BeanUtils.copyProperties(agenteDto, ag);
        agenteService.createCommandAgente(ag);
    }
}
