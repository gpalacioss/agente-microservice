package com.legosoft.facultades.repository;

import com.legosoft.facultades.models.Rol;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepository extends CrudRepository<Rol,Long> {

    @Override
    List<Rol> findAll();

    Rol findByNombre(String nombre);

}
