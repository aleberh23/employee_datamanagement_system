package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.LicenciaTomada;
import java.util.List;
import com.overnet.project_sanatorio.dto.TipoLicenciaDTO;
import com.overnet.project_sanatorio.model.TipoLicencia;
public interface ILicenciaTomadaService {
    public void saveLicenciaTomada(LicenciaTomada lictom);
    public List<LicenciaTomada> getLicenciasTomadas();
    public List<LicenciaTomada> getLicenciasTomadas(String palabra, boolean terminada);
    public LicenciaTomada findLicenciaTomadaById(int id);
    public void updateLicenciaTomada(LicenciaTomada lictom);
    public void deleteLicenciaTomada(int idLicenciaTomada);
    public List<TipoLicenciaDTO> armarTipoLicenciaDTO(int idEmpleado);
    public int sumatoriaDiasPorTipo(int idTipo, int idEmpleado);
    public TipoLicencia findById(int idTipo);
    public void verificarLicencias();
    public void verificarLicenciasInicio();
    public void eliminarLicencia(int idLicencia);
    
}
