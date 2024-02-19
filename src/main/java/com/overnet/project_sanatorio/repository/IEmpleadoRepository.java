package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.Empleado;
import jakarta.transaction.Transactional;
import java.math.BigInteger;
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
    
    @Modifying
    @Transactional
    @Query("UPDATE Empleado e SET e.baja = false WHERE e.id = ?1")
    public void darDeAlta (int empleadoId);
    
   @Query("SELECT e FROM Empleado e JOIN e.sector s " +
       "WHERE LOWER(CONCAT(TO_CHAR(e.fechaNacimiento, 'DD/MM/YYYY'), ' ', TO_CHAR(e.fechaIngreso, 'DD/MM/YYYY'), ' ', e.nombre, ' ', e.apellido, ' ', e.nroLegajo, ' ', e.nroDocumento, ' ', s.nombre)) LIKE %?1% AND e.baja = ?2 " +
       "ORDER BY e.nroLegajo ASC")
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
    
    @Query("SELECT MAX(e.nroLegajo) FROM Empleado e")
    public int maxNroLegajo();
    
    @Query("SELECT COUNT(e) > 0 FROM Empleado e WHERE e.nroLegajo = ?1")
    public boolean existeNroLegajo(BigInteger nroLegajo);
    
    @Query("SELECT COUNT(e) > 0 FROM Empleado e WHERE e.nroLegajo = ?1 AND e.nroLegajo <> ?2")
    public boolean existeNroLegajoExceptoOriginal(BigInteger nroLegajo, BigInteger nroLegajoOriginal);
    
    @Query("SELECT COUNT(e) FROM Empleado e")
    public int countEmpleados();
    
    @Query("SELECT COUNT(e) FROM Empleado e WHERE e.baja=true")
    public int countEmpleadosNoActivos();
    
    @Query("SELECT COUNT(e) FROM Empleado e WHERE e.baja=false")
    public int countEmpleadosActivos();
    
    @Query("SELECT COUNT(e) FROM Empleado e WHERE e.sector.nombre=?1")
    public int countEmpleadosPorSector(String sector);

}
