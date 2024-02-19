package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.dto.PeriodoDTO;
import com.overnet.project_sanatorio.model.LicenciaTomada;
import java.util.List;
import com.overnet.project_sanatorio.dto.TipoLicenciaDTO;
import com.overnet.project_sanatorio.model.LicenciaOrdinaria;
import com.overnet.project_sanatorio.model.TipoLicencia;
public interface ILicenciaTomadaService {
    public void saveLicenciaTomada(LicenciaTomada lictom);
    public List<LicenciaTomada> getLicenciasTomadas();
    public List<LicenciaTomada> getLicenciasTomadas(String palabra, boolean terminada);
    public LicenciaTomada findLicenciaTomadaById(int id);
    public TipoLicencia findTipoLicenciaByNombre(String nombre);
    public void updateLicenciaTomada(LicenciaTomada lictom);
    public void deleteLicenciaTomada(int idLicenciaTomada);
    public List<TipoLicenciaDTO> armarTipoLicenciaDTO(int idEmpleado, Integer anio);
    public int sumatoriaDiasPorTipoYAnio(int idTipo, int idEmpleado, int anio);
    public TipoLicencia findById(int idTipo);
    public void verificarLicencias();
    public void verificarLicenciasInicio();
    public void eliminarLicencia(int idLicencia);
    public List<LicenciaOrdinaria> obtenerLicenciasOrdinarias(int idEmpleado, Integer año);
    public List<LicenciaOrdinaria> obtenerLicenciasOrdinariasParaEditar(int idEmpleado, Integer año);
    public LicenciaTomada findLicenciaTomadaSupepuesta(int idEmpleado, LicenciaTomada nuevaLicencia);
    public LicenciaTomada findLicenciaTomadaSupepuestaExcluyendose(int idEmpleado, LicenciaTomada licenciaEditada);
    public int countLicenciasActivas();
    
}
