package com.overnet.project_sanatorio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.overnet.project_sanatorio.model.TipoLicencia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoLicenciaRepository extends JpaRepository<TipoLicencia, Integer>{
    public TipoLicencia findByNombre(String nombre);
}
