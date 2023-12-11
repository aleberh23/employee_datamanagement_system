package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Ausencia;
import com.overnet.project_sanatorio.repository.IAusenciaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AusenciaService implements IAusenciaService{
    @Autowired
    private IAusenciaRepository ausrep;

    @Override
    public List<Ausencia> getAusencias() {
        return ausrep.findAll();
    }

    @Override
    public Ausencia findAusenciaById(int idAusencia) {
        return ausrep.findById(idAusencia).orElse(null);
    }

    @Override
    public void saveAusencia(Ausencia ausencia) {
        ausrep.save(ausencia);
    }

    @Override
    public void updateAusencia(Ausencia ausencia) {
        Ausencia a = ausrep.findById(ausencia.getId()).orElse(null);
        a.setEmpleado(ausencia.getEmpleado());
        a.setDescripcion(ausencia.getDescripcion());
        a.setMotivo(ausencia.getMotivo());
        ausrep.save(a);
    }

    @Override
    public void deleteAusencia(int idAusencia) {
        ausrep.deleteById(idAusencia);
    }

    @Override
    public List<Ausencia> getAusenciasByFilter(String filter) {
        return ausrep.findByFilter(filter);
    }

}
