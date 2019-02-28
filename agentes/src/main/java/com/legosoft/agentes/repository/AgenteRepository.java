package com.legosoft.agentes.repository;

import com.legosoft.agentes.model.Agente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgenteRepository extends JpaRepository<Agente, Long> {

    Agente findByIdAgente(Long idAgente);

    List<Agente> findAll();

}
