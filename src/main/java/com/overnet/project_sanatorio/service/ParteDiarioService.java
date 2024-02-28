package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Contrato;
import com.overnet.project_sanatorio.model.DetalleParteDiario;
import com.overnet.project_sanatorio.model.Empleado;
import com.overnet.project_sanatorio.model.Inasistencia;
import com.overnet.project_sanatorio.model.LicenciaTomada;
import com.overnet.project_sanatorio.model.ParteDiario;
import com.overnet.project_sanatorio.repository.IContratoRepository;
import com.overnet.project_sanatorio.repository.IDetalleParteDiarioRepository;
import com.overnet.project_sanatorio.repository.IEmpleadoRepository;
import com.overnet.project_sanatorio.repository.IInasistenciaRepository;
import com.overnet.project_sanatorio.repository.ILicenciaTomadaRepository;
import com.overnet.project_sanatorio.repository.IParteDiarioRepository;
import com.overnet.project_sanatorio.repository.ISectorDetalleParteDiarioRepository;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParteDiarioService implements IParteDiarioService {
    
    @Autowired
    private IParteDiarioRepository pdiariorep;
    @Autowired
    private IDetalleParteDiarioRepository detpdrep;
    @Autowired
    private IEmpleadoRepository emprep;
    @Autowired
    private IContratoRepository conrep;
    @Autowired
    private ILicenciaTomadaRepository lictomrep;
    @Autowired
    private IInasistenciaRepository inarep;
    @Autowired
    private ISectorDetalleParteDiarioRepository sectorpdrep;

    @Override
    public List<ParteDiario> findAll() {
        return pdiariorep.findAll();
    }

    @Override
    public ParteDiario findByDate(LocalDate fecha) {
        if(pdiariorep.findByFecha(fecha)!=null){
            return pdiariorep.findByFecha(fecha);
        }else{
            return null;
        }
    }

    @Override
    public void save(ParteDiario pdiario) {
        pdiariorep.save(pdiario);
    }

    @Override
    public void generate(ParteDiario pdiario) {
        pdiariorep.save(pdiario);
        //jubilaciones
        LocalDate fechaFinAño = LocalDate.of(pdiario.getFechaEmision().getYear(), Month.DECEMBER, 31);
        List<Empleado> jubilaciones = emprep.findByRangoFechaJubilacion(pdiario.getFechaEmision(), fechaFinAño);
        DetalleParteDiario dpjub = new DetalleParteDiario();
        dpjub.setSector(sectorpdrep.findByNombre("Jubilaciones"));
        dpjub.setParteDiario(pdiario);
        dpjub.setEmpleados(jubilaciones);
        detpdrep.save(dpjub);
        
        //licencias
        List<LicenciaTomada>lic = lictomrep.findVigentesEnFecha(pdiario.getFechaEmision());
        DetalleParteDiario dplic = new DetalleParteDiario();
        dplic.setSector(sectorpdrep.findByNombre("Licencias"));
        dplic.setParteDiario(pdiario);
        dplic.setLicTomadas(lic);
        detpdrep.save(dplic);
        
        //contratos
        List<Contrato>con = conrep.findVigentesEnFecha(pdiario.getFechaEmision());
        DetalleParteDiario dpcon = new DetalleParteDiario();
        dpcon.setSector(sectorpdrep.findByNombre("Contratos"));
        dpcon.setParteDiario(pdiario);
        dpcon.setContratos(con);
        detpdrep.save(dpcon);
        
        //inasistencias
        List<Inasistencia>ina = inarep.findByFecha(pdiario.getFechaEmision());
        DetalleParteDiario dpina = new DetalleParteDiario();
        dpina.setSector(sectorpdrep.findByNombre("Inasistencias"));
        dpina.setParteDiario(pdiario);
        dpina.setInasistencias(ina);
        detpdrep.save(dpina);
    }

    @Override
    public ParteDiario findById(int id) {
        return pdiariorep.findById(id).orElse(null);
    }

    @Override
    public DetalleParteDiario findByIdParteDiarioAndNombreSector(int idPd, String nombreSec) {
        return detpdrep.findByParteDiarioAndSector(idPd, nombreSec);
    }

    @Override
    public void updateParteDiario(int idParteDiario) {
        ParteDiario pd = pdiariorep.findById(idParteDiario).orElse(null);
        List<DetalleParteDiario> dpds = detpdrep.findByParteDiarioId(idParteDiario);
        for (DetalleParteDiario dpd : dpds){
            if(dpd.getSector().getNombre().equals("Jubilaciones")){
                LocalDate fechaFinAño = LocalDate.of(pd.getFechaEmision().getYear(), Month.DECEMBER, 31);
                List<Empleado> jubilaciones = emprep.findByRangoFechaJubilacion(pd.getFechaEmision(), fechaFinAño);
                dpd.setEmpleados(jubilaciones);
                detpdrep.save(dpd);
            }else if(dpd.getSector().getNombre().equals("Licencias")){
                List<LicenciaTomada>licencias = lictomrep.findVigentesEnFecha(pd.getFechaEmision());
                dpd.setLicTomadas(licencias);
                detpdrep.save(dpd);
            }else if(dpd.getSector().getNombre().equals("Contratos")){
                List<Contrato>contratos=conrep.findVigentesEnFecha(pd.getFechaEmision());
                dpd.setContratos(contratos);
                detpdrep.save(dpd);
            }else if(dpd.getSector().getNombre().equals("Inasistencias")){
                List<Inasistencia>inasistencias=inarep.findByFecha(pd.getFechaEmision());
                dpd.setInasistencias(inasistencias);
                detpdrep.save(dpd);
            }
        }
        
    }

    @Override
    public List<String> getPartesDiarioForAusencia(LocalDate fecha) {
         List<String> partes = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            ParteDiario pd = pdiariorep.findByFecha(fecha);

            if (pd != null) {
                partes.add(pd.getFechaEmision().toString());
            }

            fecha = fecha.minusDays(1);
        }

        return partes;
    }
    
}
