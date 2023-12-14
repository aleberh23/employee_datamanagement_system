package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.TipoInasistencia;
import com.overnet.project_sanatorio.model.TipoLicencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoInasistenciaRepository extends JpaRepository<TipoInasistencia, Integer> {
    public TipoLicencia findByNombre(String nombre);
}
