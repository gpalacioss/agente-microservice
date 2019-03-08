package com.legosoft.cqrs.repository;

import com.legosoft.cqrs.models.Rol;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository  extends Neo4jRepository<Rol, Long> {

    Rol findByNombre(String nombre);
}
