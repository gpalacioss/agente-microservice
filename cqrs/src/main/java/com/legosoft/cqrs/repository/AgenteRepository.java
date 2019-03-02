package com.legosoft.cqrs.repository;

import com.legosoft.cqrs.models.Agente;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgenteRepository extends Neo4jRepository<Agente, Long> {

    Agente findByNombreAgente(String nombreAgente);

}
