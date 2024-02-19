package com.overnet.project_sanatorio.controller;

import com.overnet.project_sanatorio.model.Ausencia;
import com.overnet.project_sanatorio.model.Empleado;
import com.overnet.project_sanatorio.model.Inasistencia;
import com.overnet.project_sanatorio.model.LicenciaTomada;
import com.overnet.project_sanatorio.model.TipoInasistencia;
import com.overnet.project_sanatorio.service.IAusenciaService;
import com.overnet.project_sanatorio.service.IEmpleadoService;
import com.overnet.project_sanatorio.service.IInasistenciaService;
import com.overnet.project_sanatorio.service.ILicenciaTomadaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InasistenciaController {
    @Autowired
    private IAusenciaService auser;
    @Autowired
    private IInasistenciaService inasiser;
    @Autowired
    private ILicenciaTomadaService lictomser;
    @Autowired
    private IEmpleadoService empser;
    
    
    @GetMapping("/inasistencia/nueva/{id}")
    public String mostrarFormAlta(@PathVariable int id, @RequestParam(value = "idEmpleado", required = false) Integer idEmpleado,
            @RequestParam(value="inaSuperpuesta", required = false)Integer inaSuperpuesta,
            @RequestParam(value="licSuperpuesta", required = false)Integer licSuperpuesta,
            Model modelo){
        Ausencia aus = auser.findAusenciaById(id);
        Inasistencia ina = new Inasistencia();
        List<TipoInasistencia>tiposinasistencia = auser.findAllTiposInasistencia();
        ina.setEmpleado(aus.getEmpleado());
        ina.setDescripcion(aus.getDescripcion());
        ina.setFecha(aus.getFecha());
        ina.setTipoInasistencia(aus.getTipoInasistencia());
        if(idEmpleado!=null){
            Empleado emp = empser.findEmpleado(idEmpleado);
            ina.setEmpleado(emp);
            modelo.addAttribute("idEmpleado", emp.getId());
        }
        modelo.addAttribute("inasistencia", ina);
        modelo.addAttribute("id", id);
        modelo.addAttribute("tiposinasistencia", tiposinasistencia);
        // Validación 1: Fallecimiento Familiar o Enfermedad Familiar sin cargas familiares
        if (("Fallecimiento Familiar".equals(ina.getTipoInasistencia().getNombre()) ||
             "Enfermedad Familiar".equals(ina.getTipoInasistencia().getNombre())) &&
            ina.getEmpleado().getCargasDeFamilia().isEmpty()) {
            modelo.addAttribute("alertaFamilia", true);
        }

        // Validación 2: Examen sin ser estudiante
        if ("Examen".equals(ina.getTipoInasistencia().getNombre()) && !ina.getEmpleado().isEstudiante()) {
            modelo.addAttribute("alertaExamen", true);
        }
        
        //Validacion 3: no tiene dias disponibles para ese tipo
        if (inasiser.sobrepasaDaysForTipoEmpleadoAndAnio(aus.getEmpleado().getId(), aus.getTipoInasistencia().getId(), aus.getFecha().getYear())){
            modelo.addAttribute("alertaSobrepasaDias", true);
        }
        
        //si tiene alguna superpuesta
        //licencia
        if(licSuperpuesta!=null){
            LicenciaTomada licencia = lictomser.findLicenciaTomadaById(licSuperpuesta);
            modelo.addAttribute("licenciaSuperpuesta", licencia);
        }
        //inasistencia
        if(inaSuperpuesta!=null){
            Inasistencia inasistencia = inasiser.findInasistenciaById(inaSuperpuesta);
            modelo.addAttribute("inasistenciaSuperpuesta", inasistencia);
        }
        return "alta_inasistencia";
    }
    
    @PostMapping("/inasistencia/nueva/{id}")
    public String nuevaInasistencia(@PathVariable int id, @ModelAttribute("inasistencia")Inasistencia ina, @RequestParam("empleadoId") int empleadoId, Model modelo){
        LicenciaTomada licsuper = inasiser.findLicenciaSuperpuesta(empleadoId, ina.getFecha());
        Inasistencia inasuper = inasiser.findSuperpuesta(empleadoId, ina.getFecha());
        if(inasuper!=null){
            return "redirect:/inasistencia/nueva/"+id+"?inaSuperpuesta="+inasuper.getId();
        }
        if(licsuper!=null){
            return "redirect:/inasistencia/nueva/"+id+"?licSuperpuesta="+licsuper.getIdLicenciaTomada();
        }
        
        Empleado emp = empser.findEmpleado(empleadoId);
        ina.setEmpleado(emp);
        inasiser.saveInasistencia(ina);
        auser.deleteAusencia(id);
        return "redirect:/ausencia/lista";
    }
    
}
