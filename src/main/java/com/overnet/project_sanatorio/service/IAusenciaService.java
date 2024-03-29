package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Ausencia;
import com.overnet.project_sanatorio.model.TipoInasistencia;
import java.util.List;


public interface IAusenciaService {
    public List<Ausencia> getAusencias();
    public Ausencia findAusenciaById(int idAusencia);
    public void saveAusencia(Ausencia ausencia);
    public void updateAusencia(Ausencia ausencia);
    public void deleteAusencia(int idAusencia);
    public List<Ausencia> getAusenciasByFilter(String filter);
    public List<TipoInasistencia> findAllTiposInasistencia();
    public TipoInasistencia findTipoInasistenciaById(int idTipoInasistencia);
}
