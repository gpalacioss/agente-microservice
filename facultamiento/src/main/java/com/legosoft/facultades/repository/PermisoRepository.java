package com.legosoft.facultades.repository;

import com.legosoft.facultades.models.Permiso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermisoRepository extends CrudRepository<Permiso,Long> {

    @Override
    List<Permiso> findAll();

    Permiso findByNombre(String nombre);

    Permiso findByIdPermiso(Long idPermiso);

}
