package com.legosoft.cqrs.repository;

import com.legosoft.cqrs.models.Perfil;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends Neo4jRepository<Perfil, Long> {

    Perfil findByNombre(String nombre);

}
