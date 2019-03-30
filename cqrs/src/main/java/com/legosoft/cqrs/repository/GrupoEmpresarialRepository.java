package com.legosoft.cqrs.repository;

import com.legosoft.cqrs.models.GrupoEmpresarial;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoEmpresarialRepository extends Neo4jRepository<GrupoEmpresarial, Long> {

    GrupoEmpresarial findByNombreGrupo(String nombreGrupo);

}
