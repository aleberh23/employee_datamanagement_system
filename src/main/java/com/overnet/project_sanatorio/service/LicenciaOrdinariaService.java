package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.dto.LicenciaOrdinariaDTO;
import com.overnet.project_sanatorio.model.Empleado;
import com.overnet.project_sanatorio.model.LicenciaOrdinaria;
import com.overnet.project_sanatorio.repository.IEmpleadoRepository;
import com.overnet.project_sanatorio.repository.ILicenciaOrdinariaRepository;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicenciaOrdinariaService implements ILicenciaOridinariaService {
    @Autowired
    private ILicenciaOrdinariaRepository licordrep;
    @Autowired
    private IEmpleadoRepository empleadorep;

    @Override
    public List<LicenciaOrdinaria> findAll() {
        return licordrep.findAll();
    }

    @Override
    public void guardarLicOrd(LicenciaOrdinariaDTO licordDTO) {
        LicenciaOrdinaria l = new LicenciaOrdinaria();
        l.setAnio(licordDTO.getAnio());
        l.setEmpleado(empleadorep.findById(licordDTO.getIdEmp()).orElse(null));
        l.setCantidadDeDias(licordDTO.getCantDias());
        licordrep.save(l);
    }

    @Override
    public void editarLicOrd(LicenciaOrdinaria licord) {
        LicenciaOrdinaria l = licordrep.findById(licord.getIdLicenciaOrdinaria()).orElse(null);
        l.setEmpleado(licord.getEmpleado());
        l.setAnio(licord.getAnio());
        l.setCantidadDeDias(licord.getCantidadDeDias());
        licordrep.save(l);
    }

    @Override
    public HashMap<Integer, LicenciaOrdinariaDTO> crearDtos() {
        List<Empleado> emps = empleadorep.findByBajaFalse();
        HashMap<Integer, LicenciaOrdinariaDTO>ldto = new HashMap<Integer, LicenciaOrdinariaDTO>();
        int i=0;
        int anio = LocalDate.now().getYear();
        for(Empleado e : emps){
            System.out.println("EMPLEADO: "+e.getNombre() +" "+ e.getApellido());
            System.out.println("LICENCIAS ORD PARA "+anio+"="+empleadorep.contarLicenciasOrdinariasPorAnioYEmpleado(e.getId(), anio));
            if(empleadorep.contarLicenciasOrdinariasPorAnioYEmpleado(e.getId(), anio)<1){
                System.out.println("------------------");
                i++;
                System.out.println("I: "+i);
                LicenciaOrdinariaDTO l = new LicenciaOrdinariaDTO();
                l.setId(i);
                System.out.println("AÑO: "+anio);
                l.setAnio(anio);
                System.out.println("DIAS: "+e.calcularDiasDeLicencia());
                l.setCantDias(e.calcularDiasDeLicencia());
                System.out.println("NOMBRE : "+e.getNombre());
                l.setNombreEmp(e.getNombre());
                System.out.println("APELLIDO : "+e.getApellido());
                l.setApellidoEmp(e.getApellido());
                System.out.println("ID EMP : "+e.getId());
                l.setIdEmp(e.getId());
                ldto.put(i,l);
                System.out.println("------------------");
                System.out.println("GUARDADO::: KEY="+i+")->"+ldto.get(i).getNombreEmp());
                System.out.println("------------------");
            }
        }
       return ldto;
    }
    
}
