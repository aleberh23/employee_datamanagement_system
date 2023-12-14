package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.Empleado;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado, Integer>{
    @Modifying
    @Transactional
    @Query("UPDATE Empleado e SET e.baja = true WHERE e.id = ?1")
    public void darDeBaja(int empleadoId);
    
    @Query("SELECT e FROM Empleado e JOIN e.sector s " +  
       "WHERE LOWER(CONCAT(TO_CHAR(e.fechaNacimiento, 'DD/MM/YYYY'), ' ', TO_CHAR(e.fechaIngreso, 'DD/MM/YYYY'), ' ', e.nombre, ' ', e.apellido, ' ', e.nroLegajo, ' ', e.nroDocumento, ' ', s.nombre)) LIKE %?1% AND e.baja = ?2")
    public List<Empleado> findAll(String palabra, boolean deBaja);
    
    @Query("SELECT e FROM Empleado e WHERE e.baja = ?1")
    public List<Empleado> findAll(boolean deBaja);
    
    @Query("SELECT COUNT(lo) FROM licencia_ordinaria lo WHERE lo.empleado.id = ?1 AND lo.anio = ?2")
    public int contarLicenciasOrdinariasPorAnioYEmpleado(int idEmpleado, Integer anio);
    
    public List<Empleado> findByBajaFalse();
    
   @Query("SELECT e FROM Empleado e " +
       "WHERE (LOWER(CONCAT(e.nombre, e.apellido, e.nroLegajo)) LIKE LOWER(CONCAT('%', ?1, '%')))" +
       "AND e.baja = false")
    public List<Empleado> findByNombreApellidoNroLegajo(String filtro);
    
    @Query("SELECT e FROM Empleado e WHERE e.fechaJubilacion = ?1")
    public List<Empleado> findByFechaJubilacion(LocalDate fechaJubilacion);
    
    @Query("SELECT e FROM Empleado e WHERE e.fechaJubilacion BETWEEN ?1 AND ?2")
    public List<Empleado> findByRangoFechaJubilacion(LocalDate desde, LocalDate hasta);

}
