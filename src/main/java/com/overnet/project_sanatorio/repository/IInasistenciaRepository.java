package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.Inasistencia;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IInasistenciaRepository extends JpaRepository<Inasistencia, Integer>{
    @Query("SELECT i FROM Inasistencia i WHERE i.fecha = ?1")
    public List<Inasistencia> findByFecha(LocalDate fecha);
    @Query("SELECT i FROM Inasistencia i WHERE i.tipoInasistencia.id = ?1 AND i.empleado.id = ?2 AND YEAR(i.fecha) = ?3")
    public List<Inasistencia> findByEmpleadoAnioAndTipo(Integer tipoInasistenciaId, Integer empleadoId, Integer anio);
    @Query("SELECT i FROM Inasistencia i WHERE i.fecha = ?1 AND i.empleado.id = ?2")
    public Inasistencia findByFechaAndEmpleado(LocalDate fecha, Integer empleadoId);
    @Query("SELECT i FROM Inasistencia i WHERE i.empleado.id = ?1 AND (i.fecha BETWEEN ?2 AND ?3 OR i.fecha = ?2 OR i.fecha = ?3)")  
    public Inasistencia findByRangoFechasEmpleado(Integer idEmpleado, LocalDate fechaDesde, LocalDate fechaHasta);
}
