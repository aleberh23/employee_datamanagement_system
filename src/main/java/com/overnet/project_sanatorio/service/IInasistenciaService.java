package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Inasistencia;
import com.overnet.project_sanatorio.model.LicenciaTomada;
import java.time.LocalDate;
import java.util.List;


public interface IInasistenciaService {
    public List<Inasistencia> getInasistencias();
    public Inasistencia findInasistenciaById(int idInasistencia);
    public void saveInasistencia(Inasistencia inasistencia);
    public void updateInasistencia(Inasistencia inasistencia);
    public void deleteInasistencia(int idInasistencia); 
    public boolean sobrepasaDaysForTipoEmpleadoAndAnio(int idEmpleado, int idTipo, int anio);
    public Inasistencia findSuperpuesta(int idEmpleado, LocalDate fecha);
    public LicenciaTomada findLicenciaSuperpuesta(int idEmpleado, LocalDate fecha);
    public Inasistencia findSuperpuestaAltaLicencia(int idEmpleado, LocalDate fechaDesde, LocalDate fechaHasta);
}
