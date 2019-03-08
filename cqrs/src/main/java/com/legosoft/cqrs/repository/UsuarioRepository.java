package com.legosoft.cqrs.repository;

import com.legosoft.cqrs.models.Usuario;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends Neo4jRepository<Usuario, Long> {

    Usuario findByNombreUsuario(String nombreUsuario);
}
