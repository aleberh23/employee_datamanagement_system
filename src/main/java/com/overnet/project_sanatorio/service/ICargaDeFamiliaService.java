package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.CargaDeFamilia;
import java.util.List;

public interface ICargaDeFamiliaService {
    public List<CargaDeFamilia> getCargasDeFamilia();
    public CargaDeFamilia findCargaDeFamila(int idCargaFamilia);
    public void updateCargaDeFamilia(CargaDeFamilia cargaDeFamilia);
    public void bajaCargaDeFamilia(int idCargaFamilia);
    public void saveCargaDeFamilia(CargaDeFamilia cargaDeFamilia);
    public void darDeAltaCargaDeFamilia(int idCargaFamilia);
}
