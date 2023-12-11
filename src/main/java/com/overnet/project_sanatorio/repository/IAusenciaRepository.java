package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.Ausencia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IAusenciaRepository extends JpaRepository<Ausencia,Integer >{
    @Query("SELECT a FROM Ausencia a JOIN a.empleado e " +
       "WHERE LOWER(CONCAT(TO_CHAR(a.fecha, 'DD/MM/YYYY'), a.motivo, a.descripcion, " +
       "e.nombre, e.apellido, e.nroLegajo)) LIKE %?1%")
    public List<Ausencia> findByFilter(String filtro);

}
