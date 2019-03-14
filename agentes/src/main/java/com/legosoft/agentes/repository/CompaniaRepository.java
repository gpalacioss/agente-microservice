package com.legosoft.agentes.repository;

import com.legosoft.agentes.model.Compania;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompaniaRepository extends JpaRepository<Compania, Long> {

    Compania findByIdCompania(Long idCompania);

    Compania findByNombreCompania(String nombreCompania);

    List<Compania> findAll();

}
