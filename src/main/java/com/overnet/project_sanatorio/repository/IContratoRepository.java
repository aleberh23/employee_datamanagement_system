package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.Contrato;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IContratoRepository extends JpaRepository<Contrato, Integer>{
    @Modifying
    @Transactional
    @Query("UPDATE contrato c SET c.baja = true WHERE c.id = ?1")
    public void darDeBaja(int contratoId);
    @Query("SELECT c FROM contrato c WHERE c.baja = FALSE")
    public List<Contrato> findAllActivos();
    @Query("SELECT COUNT(c) FROM contrato c WHERE c.empleado.id = ?1 AND c.baja = false")  
    public int contadorContratosEmpleado(int idEmpleado);
    @Query("SELECT c FROM contrato c JOIN c.empleado e " +  
       "WHERE LOWER(CONCAT(TO_CHAR(c.fechaInicio, 'DD/MM/YYYY'), TO_CHAR(c.fechaFin, 'DD/MM/YYYY'), c.descripcion, e.nombre, e.apellido, e.nroLegajo)) LIKE %?1% AND c.baja = ?2")
    public List<Contrato> findAll(String palabra, boolean deBaja);
    @Query("SELECT c FROM contrato c WHERE c.fechaFin = ?1")
    public List<Contrato> findByFechaFin(LocalDate fechaFin);
    @Query("SELECT c FROM contrato c WHERE ?1 BETWEEN c.fechaInicio AND c.fechaFin")
    public List<Contrato> findVigentesEnFecha(LocalDate fecha);
    /*@Query("SELECT c FROM contrato c WHERE c.empleado.id = ?1 AND ((c.fechaInicio BETWEEN ?2 AND ?3) OR (c.fechaFin BETWEEN ?2 AND ?3))")
    public List<Contrato>findSuperpuesto(Integer idEmpleado, LocalDate fechaInicio, LocalDate fechaFin);*/
    @Query("SELECT c FROM contrato c WHERE c.empleado.id = ?1 AND ((c.fechaInicio BETWEEN ?2 AND ?3) OR (c.fechaFin BETWEEN ?2 AND ?3) OR (?2 BETWEEN c.fechaInicio AND c.fechaFin) OR (?3 BETWEEN c.fechaInicio AND c.fechaFin))")
    public List<Contrato> findSuperpuesto(Integer idEmpleado, LocalDate fechaInicio, LocalDate fechaFin);
    /*@Query("SELECT c FROM contrato c WHERE c.empleado.id = ?1 AND ((c.fechaInicio BETWEEN ?2 AND ?3) OR (c.fechaFin BETWEEN ?2 AND ?3)) AND c.idContrato <> ?4")
    public List<Contrato> findSuperpuestoEditar(Integer idEmpleado, LocalDate fechaInicio, LocalDate fechaFin, Integer idExcluir);*/
    @Query("SELECT c FROM contrato c WHERE c.empleado.id = ?1 AND ((c.fechaInicio BETWEEN ?2 AND ?3) OR (c.fechaFin BETWEEN ?2 AND ?3) OR (?2 BETWEEN c.fechaInicio AND c.fechaFin) OR (?3 BETWEEN c.fechaInicio AND c.fechaFin)) AND c.idContrato <> ?4")
    public List<Contrato> findSuperpuestoEditar(Integer idEmpleado, LocalDate fechaInicio, LocalDate fechaFin, Integer idExcluir);
    @Query("SELECT MAX(c.fechaFin) from contrato c WHERE c.empleado.id = ?1 AND c.estado <> 'Cancelado'")
    public LocalDate obtenerUltimaFechaFin(Integer idEmpleado);
    @Query("SELECT MAX(c.fechaFin) FROM contrato c WHERE c.empleado.id = ?1 AND c.id <> ?2 AND c.estado <> 'Cancelado'")
    public LocalDate obtenerUltimaFechaFinExceptoContrato(Integer idEmpleado, Integer idContrato);
    @Query("SELECT COUNT(c) FROM contrato c WHERE c.baja=false")
    public int countContratosActivos();
    @Query("SELECT c FROM contrato c WHERE c.fechaFin BETWEEN ?1 AND ?2 AND c.baja = false AND c.empleado.id = ?3")
    public Contrato findContratoProximoAFinalizarPorEmpleado(LocalDate fechaInicio, LocalDate fechaFin, int idEmpleado);
}
