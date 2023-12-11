package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Inasistencia;
import java.util.List;


public interface IInasistenciaService {
    public List<Inasistencia> getInasistencias();
    public Inasistencia findInasistenciaById(int idInasistencia);
    public void saveInasistencia(Inasistencia inasistencia);
    public void updateInasistencia(Inasistencia inasistencia);
    public void deleteInasistencia(int idInasistencia); 
}
