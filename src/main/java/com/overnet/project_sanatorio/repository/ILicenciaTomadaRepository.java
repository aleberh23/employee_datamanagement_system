package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.LicenciaTomada;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ILicenciaTomadaRepository extends JpaRepository<LicenciaTomada, Integer> {
   
    public List<LicenciaTomada> findByEmpleado_IdAndTipoLicencia_Id(Integer idEmpleado, Integer idTipoLicencia);
    @Query("SELECT lt FROM licencia_tomada lt WHERE lt.tipoLicencia.id = ?1 AND lt.empleado.id = ?2 AND lt.fechaDesde >= ?3 AND lt.fechaHasta <= ?4")  
    public List<LicenciaTomada> findByTipoEmpleadoYRangoFechas(Integer idTipoLicencia, Integer idEmpleado, LocalDate inicioPeriodo, LocalDate finPeriodo);
    @Query("SELECT l FROM licencia_tomada l WHERE l.terminada = false")
    public List<LicenciaTomada> findAllVigentes();
    @Query("SELECT lt FROM licencia_tomada lt JOIN lt.empleado empl JOIN lt.tipoLicencia tl " +  
       "WHERE LOWER(CONCAT(TO_CHAR(lt.fechaDesde, 'DD/MM/YYYY'), TO_CHAR(lt.fechaHasta, 'DD/MM/YYYY'), " + 
                         "lt.descripcion, tl.nombre, empl.nombre, empl.apellido)) LIKE %?1% AND lt.terminada = ?2")
    public List<LicenciaTomada> buscar(String palabra, boolean terminada);
    @Query("SELECT lt FROM licencia_tomada lt WHERE lt.fechaHasta = ?1")
    public List<LicenciaTomada> findByFechaHasta(LocalDate fechaHasta);
    @Query("DELETE FROM licencia_tomada")
    void deleteAllLicenciasTomadas();
    @Query("SELECT lt FROM licencia_tomada lt WHERE ?1 BETWEEN lt.fechaDesde AND lt.fechaHasta")
    public List<LicenciaTomada> findVigentesEnFecha(LocalDate fecha);
    
}
