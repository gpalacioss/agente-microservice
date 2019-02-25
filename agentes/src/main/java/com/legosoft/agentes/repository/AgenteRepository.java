package com.legosoft.agentes.repository;

import com.legosoft.agentes.model.Agente;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AgenteRepository extends CrudRepository<Agente, Long> {

    Agente findByIdAgente(Long idAgente);

    List<Agente> findAll();

}
