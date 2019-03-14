package com.legosoft.agentes.controller;

import com.legosoft.agentes.dtos.CompaniaDto;
import com.legosoft.agentes.model.Compania;
import com.legosoft.agentes.service.CompaniaService;
import com.legosoft.agentes.utils.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/compania")
public class CompaniaController {

    @Autowired
    private CompaniaService companiaService;


    @GetMapping("/getAllCompania")
    public List<Compania> getAllCompanias(){
        return companiaService.findAllCompanias();
    }

    @GetMapping("/getCompania/{idCompania}")
    public Compania getCompaniaById(@PathVariable("idCompania") Long idCompania){
        return companiaService.findCompaniaById(idCompania);
    }

    @PostMapping("/saveCompania")
    public Response createCompania(@RequestBody CompaniaDto companiaDto){
        Compania com = new Compania();
        BeanUtils.copyProperties(companiaDto, com);
        com.setFechaCreacion(new Date());
        com.setEstatus("PENDIENTE_ASIGNAR_USUARIO");
        Response response = companiaService.createCompaniaCommand(com);
        return  response;
    }

    @PutMapping("/updateCompania")
    public void updateCompania(@RequestBody CompaniaDto companiaDto){
        Compania comp = new Compania();
        BeanUtils.copyProperties(companiaDto, comp);
        companiaService.relacionarUsuarioCompania(comp);
    }



    @DeleteMapping("/deleteCompania")
    public void deleteCompania(@RequestBody Compania compania){
        Compania a = companiaService.findCompaniaById(compania.getIdCompania());
        if (a != null){
            companiaService.deleteCompania(a);
        }
    }

}
