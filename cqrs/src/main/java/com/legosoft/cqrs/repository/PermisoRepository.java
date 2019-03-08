package com.legosoft.cqrs.repository;

import com.legosoft.cqrs.models.Permiso;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermisoRepository extends Neo4jRepository<Permiso, Long> {

    Permiso findByNombre(String nombre);

}
