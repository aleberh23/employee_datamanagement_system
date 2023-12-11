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
        List<Empleado> jubilaciones = emprep.findByFechaJubilacion(pdiario.getFechaEmision());
        DetalleParteDiario dpjub = new DetalleParteDiario();
        dpjub.setSector(sectorpdrep.findByNombre("Jubilaciones"));
        dpjub.setParteDiario(pdiario);
        dpjub.setEmpleados(jubilaciones);
        detpdrep.save(dpjub);
        
        //licencias que terminan
        List<LicenciaTomada>lic = lictomrep.findByFechaHasta(pdiario.getFechaEmision());
        DetalleParteDiario dplic = new DetalleParteDiario();
        dplic.setSector(sectorpdrep.findByNombre("Finalizaciones de licencias"));
        dplic.setParteDiario(pdiario);
        dplic.setLicTomadas(lic);
        detpdrep.save(dplic);
        
        //contratos que finalizan
        List<Contrato>con = conrep.findByFechaFin(pdiario.getFechaEmision());
        DetalleParteDiario dpcon = new DetalleParteDiario();
        dpcon.setSector(sectorpdrep.findByNombre("Finalizaciones de contratos"));
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
    
}
