package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.Empleado;
import jakarta.transaction.Transactional;
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
    @Query("SELECT e FROM Empleado e"
            + " WHERE (CONCAT(e.nombre, e.apellido, e.id,"
            + " e.correoElectronico, e.nroLegajo, e.nroDocumento, e.nroCelular,"
            + " e.cuil) LIKE %?1%)"
            + " AND e.baja = ?2")
    public List<Empleado> findAll(String palabra, boolean deBaja);
    @Query("SELECT e FROM Empleado e WHERE e.baja = ?1")
    public List<Empleado> findAll(boolean deBaja);
    
    @Query("SELECT COUNT(lo) FROM licencia_ordinaria lo WHERE lo.empleado.id = ?1 AND lo.anio = ?2")
    public int contarLicenciasOrdinariasPorAnioYEmpleado(int idEmpleado, Integer anio);
    
    public List<Empleado> findByBajaFalse();
}
