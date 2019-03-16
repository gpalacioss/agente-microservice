package com.legosoft.cqrs.repository;

import com.legosoft.cqrs.models.Compania;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompaniaRepository extends Neo4jRepository<Compania, Long> {

    Compania findByNombreCompania(String nombreCompania);

}
