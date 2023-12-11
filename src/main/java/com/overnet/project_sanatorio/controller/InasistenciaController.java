package com.overnet.project_sanatorio.controller;

import com.overnet.project_sanatorio.model.Ausencia;
import com.overnet.project_sanatorio.model.Empleado;
import com.overnet.project_sanatorio.model.Inasistencia;
import com.overnet.project_sanatorio.service.IAusenciaService;
import com.overnet.project_sanatorio.service.IEmpleadoService;
import com.overnet.project_sanatorio.service.IInasistenciaService;
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
    private IEmpleadoService empser;
    
    
    @GetMapping("/inasistencia/nueva/{id}")
    public String mostrarFormAlta(@PathVariable int id, @RequestParam(value = "idEmpleado", required = false) Integer idEmpleado, Model modelo){
        Ausencia aus = auser.findAusenciaById(id);
        Inasistencia ina = new Inasistencia();
        ina.setEmpleado(aus.getEmpleado());
        ina.setDescripcion(aus.getDescripcion());
        ina.setFecha(aus.getFecha());
        ina.setMotivo(aus.getMotivo());
        if(idEmpleado!=null){
            Empleado emp = empser.findEmpleado(idEmpleado);
            ina.setEmpleado(emp);
            modelo.addAttribute("idEmpleado", emp.getId());
        }
        modelo.addAttribute("inasistencia", ina);
        modelo.addAttribute("id", id);
        
        return "alta_inasistencia";
    }
    
    @PostMapping("/inasistencia/nueva/{id}")
    public String nuevaInasistencia(@PathVariable int id, @ModelAttribute("inasistencia")Inasistencia ina, @RequestParam("empleadoId") int empleadoId, Model modelo){
        Empleado emp = empser.findEmpleado(empleadoId);
        ina.setEmpleado(emp);
        inasiser.saveInasistencia(ina);
        auser.deleteAusencia(id);
        return "redirect:/ausencia/lista";
    }
    
}
