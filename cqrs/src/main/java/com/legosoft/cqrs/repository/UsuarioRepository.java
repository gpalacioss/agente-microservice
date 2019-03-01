package com.legosoft.cqrs.repository;

import com.legosoft.cqrs.models.Usuario;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UsuarioRepository extends Neo4jRepository<Usuario, Long> {
}
