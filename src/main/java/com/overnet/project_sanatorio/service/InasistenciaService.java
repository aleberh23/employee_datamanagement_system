package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Inasistencia;
import com.overnet.project_sanatorio.repository.IInasistenciaRepository;
import com.overnet.project_sanatorio.repository.ITipoInasistenciaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InasistenciaService implements IInasistenciaService{
    @Autowired
    private IInasistenciaRepository inasirep;
    @Autowired
    private ITipoInasistenciaRepository tipoinrep;
    @Override
    public List<Inasistencia> getInasistencias() {
        return inasirep.findAll();
    }

    @Override
    public Inasistencia findInasistenciaById(int idInasistencia) {
        return inasirep.findById(idInasistencia).orElse(null);
    }

    @Override
    public void saveInasistencia(Inasistencia inasistencia) {
        inasirep.save(inasistencia);
    }

    @Override
    public void updateInasistencia(Inasistencia inasistencia) {
        Inasistencia i = inasirep.findById(inasistencia.getId()).orElse(null);
        i.setDescripcion(inasistencia.getDescripcion());
        i.setEmpleado(inasistencia.getEmpleado());
        i.setTipoInasistencia(inasistencia.getTipoInasistencia());
        i.setCertificado(inasistencia.isCertificado());
        inasirep.save(i);
    }

    @Override
    public void deleteInasistencia(int idInasistencia) {
        inasirep.deleteById(idInasistencia);
    }
}
