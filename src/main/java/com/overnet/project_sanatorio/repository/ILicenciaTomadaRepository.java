package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.LicenciaTomada;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ILicenciaTomadaRepository extends JpaRepository<LicenciaTomada, Integer> {
   
    public List<LicenciaTomada> findByEmpleado_IdAndTipoLicencia_Id(Integer idEmpleado, Integer idTipoLicencia);
    @Query("SELECT l FROM licencia_tomada l WHERE l.terminada = false")
    public List<LicenciaTomada> findAllVigentes();
    @Query("SELECT lt FROM licencia_tomada lt JOIN lt.empleado empl JOIN lt.tipoLicencia tl " +  
       "WHERE LOWER(CONCAT(TO_CHAR(lt.fechaDesde, 'DD/MM/YYYY'), TO_CHAR(lt.fechaHasta, 'DD/MM/YYYY'), " + 
                         "lt.descripcion, tl.nombre, empl.nombre, empl.apellido)) LIKE %?1% AND lt.terminada = ?2")
    public List<LicenciaTomada> buscar(String palabra, boolean terminada);
    
}
