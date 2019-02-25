package com.legosoft.agentes.service.impl;

import com.legosoft.agentes.model.Agente;
import com.legosoft.agentes.repository.AgenteRepository;
import com.legosoft.agentes.service.AgenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("agenteService")
public class agenteServiceImpl implements AgenteService {

    @Autowired
    private AgenteRepository agenteRepository;


    public Agente findAgenteById(Long idAgente){
        return agenteRepository.findByIdAgente(idAgente);
    }

    public List<Agente> findAllAgentes(){
        return agenteRepository.findAll();
    }

    public Agente saveOrUpdate(Agente agente){
        return agenteRepository.save(agente);
    }

}
