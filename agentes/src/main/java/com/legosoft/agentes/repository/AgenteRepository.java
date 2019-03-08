package com.legosoft.agentes.repository;

import com.legosoft.agentes.model.Agente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgenteRepository extends JpaRepository<Agente, Long> {

    Agente findByIdAgente(Long idAgente);

    Agente findByNombreAgente(String nombreAgente);

    List<Agente> findAll();

}
