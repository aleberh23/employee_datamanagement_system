package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.dto.PeriodoDTO;
import com.overnet.project_sanatorio.dto.TipoLicenciaDTO;
import com.overnet.project_sanatorio.model.LicenciaOrdinaria;
import com.overnet.project_sanatorio.model.LicenciaTomada;
import com.overnet.project_sanatorio.model.TipoLicencia;
import com.overnet.project_sanatorio.repository.IEmpleadoRepository;
import com.overnet.project_sanatorio.repository.ILicenciaOrdinariaRepository;
import com.overnet.project_sanatorio.repository.ILicenciaTomadaRepository;
import com.overnet.project_sanatorio.repository.ITipoLicenciaRepository;
import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class LicenciaTomadaService implements ILicenciaTomadaService {

    @Autowired
    private ILicenciaTomadaRepository lictomrep;
    @Autowired
    private ITipoLicenciaRepository tipolicrep;
    @Autowired
    private IEmpleadoRepository empleadorep;
    @Autowired
    private ILicenciaOrdinariaRepository licordrep;

    @Override
    public void saveLicenciaTomada(LicenciaTomada lictom) {
        lictomrep.save(lictom);
    }

    @Override
    public List<LicenciaTomada> getLicenciasTomadas() {
        return lictomrep.findAll();
    }

    @Override
    public LicenciaTomada findLicenciaTomadaById(int id) {
        return lictomrep.findById(id).orElse(null);
    }

    @Override
    public void updateLicenciaTomada(LicenciaTomada lictom) {
        lictomrep.save(lictom);

    }

    @Override
    public void deleteLicenciaTomada(int idLicenciaTomada) {
        lictomrep.deleteById(idLicenciaTomada);
    }

    @Override
    public List<TipoLicenciaDTO> armarTipoLicenciaDTO(int idEmpleado, Integer anio) {
        List<TipoLicenciaDTO> dtos = new ArrayList<TipoLicenciaDTO>();
        PeriodoDTO periodo = new PeriodoDTO(anio);
        List<TipoLicencia> tipos = tipolicrep.findAll();
        tipos.forEach(t -> {
            if (!t.getNombre().equals("Ordinaria")) {
                TipoLicenciaDTO tdto = new TipoLicenciaDTO();
                tdto.setIdTipo(t.getId());
                tdto.setNombre(t.getNombre());
                tdto.setDiasTotales(t.getCantidadDias());
                tdto.setDiasTomados(sumatoriaDiasPorTipoYPeriodo(t.getId(), idEmpleado, periodo));
                if(tdto.getDiasTotales()!=null){
                        tdto.setDiasRestantes(tdto.getDiasTotales()-tdto.getDiasTomados());
                    }else{
                        tdto.setDiasRestantes(null);
                    }
                dtos.add(tdto);
            }
        });
        return dtos;
    }

    @Override
    public int sumatoriaDiasPorTipoYPeriodo(int idTipo, int idEmpleado, PeriodoDTO periodo) {
        int sum = 0;
        List<LicenciaTomada> licencias = lictomrep.findByTipoEmpleadoYRangoFechas(idTipo, idEmpleado, periodo.getFechaDesde(), periodo.getFechaHasta());
        for (LicenciaTomada l : licencias) {
            int dias = (int) ChronoUnit.DAYS.between(l.getFechaDesde(), l.getFechaHasta());
            sum += dias;
        }
        return sum;
    }

    @Override
    public TipoLicencia findById(int idTipo) {
        return tipolicrep.findById(idTipo).orElse(null);
    }

    @Override
    public List<LicenciaTomada> getLicenciasTomadas(String palabra, boolean terminada) {
        return lictomrep.buscar(palabra, terminada);
    }
    @Scheduled(cron = "0 0 0 * * ?") // Ejecutar todos los días a medianoche
    @Override
    public void verificarLicencias() {
        List<LicenciaTomada>lic=lictomrep.findAllVigentes();
        LocalDate fechaActual = LocalDate.now();
        for(LicenciaTomada l : lic){
            if(l.getFechaHasta()!=null && l.getFechaHasta().isBefore(fechaActual)){
                l.setTerminada(true);
                lictomrep.save(l);
            }
        }
    }
    @PostConstruct //ejecutar cuando arranca el sistema
    @Override
    public void verificarLicenciasInicio() {
       verificarLicencias();
    }

    @Override
    public void eliminarLicencia(int idLicencia) {
        lictomrep.deleteById(idLicencia);
    }

    @Override
    public List<LicenciaOrdinaria> obtenerLicenciasOrdinarias(int idEmpleado, Integer anio) {
        List<LicenciaOrdinaria> licords= new ArrayList<>();
        if(licordrep.findByEmpleadoAndAnio(idEmpleado, anio)!=null){
            LicenciaOrdinaria l = licordrep.findByEmpleadoAndAnio(idEmpleado, anio);
            if(l.getDiasRestanes()>0){
                licords.add(l);
            }
            if(licordrep.findByEmpleadoAndAnio(idEmpleado, anio-1)!=null){
                LicenciaOrdinaria l2 = licordrep.findByEmpleadoAndAnio(idEmpleado, anio-1);
                if(l2.getDiasRestanes()>0){
                    licords.add(l2);
                }
            }
        }
        return licords;
    }

    @Override
    public TipoLicencia findTipoLicenciaByNombre(String nombre) {
        return tipolicrep.findByNombre(nombre);
    }

    @Override
    public List<LicenciaOrdinaria> obtenerLicenciasOrdinariasParaEditar(int idEmpleado, Integer anio) {
        List<LicenciaOrdinaria> licords= new ArrayList<>();
        if(licordrep.findByEmpleadoAndAnio(idEmpleado, anio)!=null){
            LicenciaOrdinaria l = licordrep.findByEmpleadoAndAnio(idEmpleado, anio);
            licords.add(l);
            if(licordrep.findByEmpleadoAndAnio(idEmpleado, anio-1)!=null){
                LicenciaOrdinaria l2 = licordrep.findByEmpleadoAndAnio(idEmpleado, anio-1);
                licords.add(l2);
            }
        }
        return licords;
    }

}
