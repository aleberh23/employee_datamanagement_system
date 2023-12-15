package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.CargaDeFamilia;
import com.overnet.project_sanatorio.repository.ICargaDeFamiliaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CargaDeFamiliaService implements ICargaDeFamiliaService {
    @Autowired
    private ICargaDeFamiliaRepository cargarep;
 
    @Override
    public List<CargaDeFamilia> getCargasDeFamilia() {
        return cargarep.findAll();
    }

    @Override
    public CargaDeFamilia findCargaDeFamila(int idCargaFamilia) {
        return cargarep.findById(idCargaFamilia).orElse(null);
    }

    @Override
    public void updateCargaDeFamilia(CargaDeFamilia cargaDeFamilia) {
        cargarep.save(cargaDeFamilia);
    }

    @Override
    public void bajaCargaDeFamilia(int idCargaFamilia) {
        cargarep.darDeBaja(idCargaFamilia);
    }

    @Override
    public void saveCargaDeFamilia(CargaDeFamilia cargaDeFamilia) {
        cargarep.save(cargaDeFamilia);
    }

    @Override
    public void darDeAltaCargaDeFamilia(int idCargaFamilia) {
        cargarep.darDeAlta(idCargaFamilia);
    }
    
}
