package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Inasistencia;
import com.overnet.project_sanatorio.model.LicenciaTomada;
import com.overnet.project_sanatorio.model.TipoInasistencia;
import com.overnet.project_sanatorio.model.TipoLicencia;
import com.overnet.project_sanatorio.repository.IInasistenciaRepository;
import com.overnet.project_sanatorio.repository.ILicenciaTomadaRepository;
import com.overnet.project_sanatorio.repository.ITipoInasistenciaRepository;
import com.overnet.project_sanatorio.repository.ITipoLicenciaRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InasistenciaService implements IInasistenciaService{
    @Autowired
    private IInasistenciaRepository inasirep;
    @Autowired
    private ILicenciaTomadaRepository lictomrep;
    @Autowired
    private ITipoInasistenciaRepository tipoinrep;
    @Autowired
    private ITipoLicenciaRepository tipolicrep;
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

    @Override
    public boolean sobrepasaDaysForTipoEmpleadoAndAnio(int idEmpleado, int idTipo, int anio) {
        TipoInasistencia tipoina =tipoinrep.findById(idTipo).orElse(null);
        TipoLicencia tipolic = tipolicrep.findByNombre(tipoina.getNombre());
        if(tipolic.getCantidadDias()!=null){
            int sum=0;
            List<LicenciaTomada> licencias = lictomrep.findByTipoEmpleadoYAnio(tipolic.getId(), idEmpleado, anio);
            List<Inasistencia> inasistencias = inasirep.findByEmpleadoAnioAndTipo(idTipo, idEmpleado, anio);
            for(LicenciaTomada lic : licencias){
                int diferenciaEnDias = (int)ChronoUnit.DAYS.between(lic.getFechaDesde(), lic.getFechaHasta());
                sum+=diferenciaEnDias;
            }
            for(Inasistencia ina : inasistencias){
                sum++;
            }
            if(sum>=tipolic.getCantidadDias()){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    @Override
    public Inasistencia findSuperpuesta(int idEmpleado, LocalDate fecha) {
        return inasirep.findByFechaAndEmpleado(fecha, idEmpleado);
    }

    @Override
    public LicenciaTomada findLicenciaSuperpuesta(int idEmpleado, LocalDate fecha) {
        return lictomrep.findSuperpuestaAltaIna(idEmpleado, fecha);
    }

    @Override
    public Inasistencia findSuperpuestaAltaLicencia(int idEmpleado, LocalDate fechaDesde, LocalDate fechaHasta) {
        return inasirep.findByRangoFechasEmpleado(idEmpleado, fechaDesde, fechaHasta);
    }
}
