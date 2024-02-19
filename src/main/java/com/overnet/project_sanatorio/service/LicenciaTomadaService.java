package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.dto.PeriodoDTO;
import com.overnet.project_sanatorio.dto.TipoLicenciaDTO;
import com.overnet.project_sanatorio.model.Inasistencia;
import com.overnet.project_sanatorio.model.LicenciaOrdinaria;
import com.overnet.project_sanatorio.model.LicenciaTomada;
import com.overnet.project_sanatorio.model.TipoInasistencia;
import com.overnet.project_sanatorio.model.TipoLicencia;
import com.overnet.project_sanatorio.repository.IEmpleadoRepository;
import com.overnet.project_sanatorio.repository.IInasistenciaRepository;
import com.overnet.project_sanatorio.repository.ILicenciaOrdinariaRepository;
import com.overnet.project_sanatorio.repository.ILicenciaTomadaRepository;
import com.overnet.project_sanatorio.repository.ITipoInasistenciaRepository;
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
    @Autowired
    private IInasistenciaRepository inarep;
    @Autowired
    private ITipoInasistenciaRepository tipoinarep;

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
        List<TipoLicencia> tipos = tipolicrep.findAll();
        tipos.forEach(t -> {
            if (!t.getNombre().equals("Ordinaria")) {
                TipoLicenciaDTO tdto = new TipoLicenciaDTO();
                tdto.setIdTipo(t.getId());
                tdto.setNombre(t.getNombre());
                tdto.setDiasTotales(t.getCantidadDias());
                tdto.setDiasTomados(sumatoriaDiasPorTipoYAnio(t.getId(), idEmpleado, anio));
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
    //•	Los dias disponibles para los tipos de licencia/inasistencia son ANUALES, AÑO CALENDARIO.
    public int sumatoriaDiasPorTipoYAnio(int idTipo, int idEmpleado, int anio) {
        int sum = 0;
        List<LicenciaTomada> licencias = lictomrep.findByTipoEmpleadoYAnio(idTipo, idEmpleado, anio);
        for (LicenciaTomada l : licencias) {
            int dias = (int) ChronoUnit.DAYS.between(l.getFechaDesde(), l.getFechaHasta());
            sum += dias;
        }
        TipoLicencia tipo = tipolicrep.findById(idTipo).orElse(null);
        //buscamos el tipo de inasistencia para hacer la sumatoria de dias ocupados por inasistencias tambien.
        if(tipo.getNombre().equals("Enfermedad Familiar") || tipo.getNombre().equals("Fallecimiento Familiar") || tipo.getNombre().equals("Examen") || tipo.getNombre().equals("Enfermedad")){
            TipoInasistencia tipoina = tipoinarep.findByNombre(tipo.getNombre());
            List<Inasistencia> inasistencias = inarep.findByEmpleadoAnioAndTipo(tipoina.getId(), idEmpleado, anio);
            for(Inasistencia ina : inasistencias){
                if(ina.getFecha().getYear()==anio){
                    sum++;
                }
            }
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
        System.out.println("ENTRO A obtenerLicenciasOrdinarias("+idEmpleado+",  "+anio+")");
        List<LicenciaOrdinaria> licords= new ArrayList<>();
        if(licordrep.findByEmpleadoAndAnio(idEmpleado, anio)!=null){
            LicenciaOrdinaria l = licordrep.findByEmpleadoAndAnio(idEmpleado, anio);
            System.out.println("Año: "+l.getAnio()+"|Cant dias total: "+l.getCantidadDeDias()+"|Dias restantes: "+l.getDiasRestanes());
            if(l.getDiasRestanes()>0){
                licords.add(l);
            }
            if(licordrep.findByEmpleadoAndAnio(idEmpleado, anio-1)!=null){
                LicenciaOrdinaria l2 = licordrep.findByEmpleadoAndAnio(idEmpleado, anio-1);
                System.out.println("Año: "+l2.getAnio()+"|Cant dias total: "+l2.getCantidadDeDias()+"|Dias restantes: "+l2.getDiasRestanes());
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

    @Override
    public LicenciaTomada findLicenciaTomadaSupepuesta(int idEmpleado, LicenciaTomada nuevaLicencia) {
        List<LicenciaTomada>superpuestas=lictomrep.findSuperpuesta(idEmpleado, nuevaLicencia.getFechaDesde(), nuevaLicencia.getFechaHasta());
        LicenciaTomada licenciaSuperpuesta = superpuestas.stream().findFirst().orElse(null);
        return licenciaSuperpuesta;
    }

    @Override
    public LicenciaTomada findLicenciaTomadaSupepuestaExcluyendose(int idEmpleado, LicenciaTomada licenciaEditada) {
        System.out.println("ID licencia tomada: "+ licenciaEditada.getIdLicenciaTomada());
        System.out.println("fecha desde: "+ licenciaEditada.getFechaDesde());
        System.out.println("fecha hasta: "+licenciaEditada.getFechaHasta());
        List<LicenciaTomada>superpuestas=lictomrep.findSuperpuestaEditar(idEmpleado, licenciaEditada.getFechaDesde(), licenciaEditada.getFechaHasta(), licenciaEditada.getIdLicenciaTomada());
        LicenciaTomada licenciaSuperpuesta = superpuestas.stream().findFirst().orElse(null);
        return licenciaSuperpuesta;
    }

    @Override
    public int countLicenciasActivas() {
        return lictomrep.countLicenciasActivas();
    }

}
