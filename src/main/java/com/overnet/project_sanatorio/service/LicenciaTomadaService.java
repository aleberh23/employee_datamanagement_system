package com.overnet.project_sanatorio.service;

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
    public List<TipoLicenciaDTO> armarTipoLicenciaDTO(int idEmpleado) {
        List<TipoLicenciaDTO> dtos = new ArrayList<TipoLicenciaDTO>();
        LocalDate fechaActual = LocalDate.now();
        int anio = fechaActual.getYear();
        List<TipoLicencia> tipos = tipolicrep.findAll();
        tipos.forEach(t -> {
            if (!t.getNombre().equals("Vacaciones")) {
                TipoLicenciaDTO tdto = new TipoLicenciaDTO();
                tdto.setIdTipo(t.getId());
                tdto.setNombre(t.getNombre());
                tdto.setDiasTotales(t.getCantidadDias());
                tdto.setDiasTomados(sumatoriaDiasPorTipo(t.getId(), idEmpleado));
                if(tdto.getDiasTotales()!=null){
                        tdto.setDiasRestantes(tdto.getDiasTotales()-tdto.getDiasTomados());
                    }else{
                        tdto.setDiasRestantes(null);
                    }
                dtos.add(tdto);
                /*System.out.println("DTO ID TIPO: " + tdto.getIdTipo()
                        + "\nNOMBRE: " + tdto.getNombre()
                        + "\nDIAS TOTALES " + tdto.getDiasTotales()
                        + "\nDIAS TOMADOS: " + tdto.getDiasTomados()
                        + "\nDIAS RESTANTES: " + tdto.getDiasRestantes());
                System.out.println("---------------------------");*/
            } else {
                LicenciaOrdinaria licord = licordrep.findByEmpleadoAndAnio(idEmpleado, anio);
                if (licord != null) {
                    TipoLicenciaDTO tdto = new TipoLicenciaDTO();
                    TipoLicencia tipolic = tipolicrep.findByNombre("Vacaciones");
                    tdto.setIdTipo(tipolic.getId());
                    tdto.setNombre(tipolic.getNombre());
                    tdto.setDiasTotales(licord.getCantidadDeDias());
                    tdto.setDiasTomados(sumatoriaDiasPorTipo(t.getId(), idEmpleado));
                    if(tdto.getDiasTotales()!=null){
                        tdto.setDiasRestantes(tdto.getDiasTotales()-tdto.getDiasTomados());
                    }else{
                        tdto.setDiasRestantes(null);
                    }
                    dtos.add(tdto);
                    /*System.out.println("DTO ID TIPO: " + tdto.getIdTipo()
                            + "\nNOMBRE: " + tdto.getNombre()
                            + "\nDIAS TOTALES " + tdto.getDiasTotales()
                            + "\nDIAS TOMADOS: " + tdto.getDiasTomados()
                            + "\nDIAS RESTANTES: " + tdto.getDiasRestantes());
                    System.out.println("---------------------------");*/
                }
            }
        });
        return dtos;
    }

    @Override
    public int sumatoriaDiasPorTipo(int idTipo, int idEmpleado) {
        int sum = 0;
        List<LicenciaTomada> licencias = lictomrep.findByEmpleado_IdAndTipoLicencia_Id(idEmpleado, idTipo);
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

}
