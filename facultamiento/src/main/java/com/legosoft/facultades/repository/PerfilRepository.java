package com.legosoft.facultades.repository;

import com.legosoft.facultades.models.Perfil;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfilRepository extends CrudRepository<Perfil,Long> {

    @Override
    List<Perfil> findAll();

    Perfil findByNombre(String nombre);

}
