package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Ausencia;
import com.overnet.project_sanatorio.model.TipoInasistencia;
import com.overnet.project_sanatorio.repository.IAusenciaRepository;
import com.overnet.project_sanatorio.repository.ITipoInasistenciaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AusenciaService implements IAusenciaService{
    @Autowired
    private IAusenciaRepository ausrep;
    @Autowired
    private ITipoInasistenciaRepository tipoinrep;
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
        a.setTipoInasistencia(ausencia.getTipoInasistencia());
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

    @Override
    public List<TipoInasistencia> findAllTiposInasistencia() {
        return tipoinrep.findAll();
    }

    @Override
    public TipoInasistencia findTipoInasistenciaById(int idTipoInasistencia) {
        return tipoinrep.findById(idTipoInasistencia).orElse(null);
    }

}
