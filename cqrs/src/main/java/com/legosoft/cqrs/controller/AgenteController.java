package com.legosoft.cqrs.controller;

import com.legosoft.cqrs.dtos.AgenteDto;
import com.legosoft.cqrs.models.Agente;
import com.legosoft.cqrs.models.Compania;
import com.legosoft.cqrs.service.CompaniaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/companias")
public class AgenteController {

    @Autowired
    private CompaniaService companiaService;

    @PostMapping("/createAgente")
    public void createCompania(@RequestBody AgenteDto agenteDto){
        Compania comp = new Compania();
        BeanUtils.copyProperties(agenteDto, comp);
        companiaService.createCommandCompania(comp);
    }
}
