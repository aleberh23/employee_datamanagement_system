package com.overnet.project_sanatorio.controller;

import com.overnet.project_sanatorio.model.Domicilio;
import com.overnet.project_sanatorio.model.Empleado;
import com.overnet.project_sanatorio.service.EmpleadoService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoser;
    
    
   @GetMapping("/empleado/alta")
    public String mostrarFormAltaEstudiante(Model modelo){
        Empleado emp = new Empleado();
        modelo.addAttribute("empleado", emp);
        return "alta_empleado";
    }
    @PostMapping("/empleado/alta")
    public String guardarEmpleado(@ModelAttribute("empleado")Empleado emp){
    empleadoser.saveEmpleado(emp);
    return "redirect:/empleado/alta";
    }
    @GetMapping("empleado/ver")
    public String traerEmpleados(Model modelo, @Param("palabra")String palabra, @Param("deBaja")boolean deBaja){
        
        if (palabra == null) {
            palabra = ""; //
        }
        System.out.println(!deBaja);
        List<Empleado> empleados = empleadoser.getEmpleados(palabra, !deBaja);
        modelo.addAttribute("empleados", empleados);
        modelo.addAttribute("palabra", palabra);
        modelo.addAttribute("deBaja", deBaja);
       
    return "vista_empleados";
    }
    
    @GetMapping("empleado/editar/{id}")
    public String mostrarFormEditar(@PathVariable int id, Model modelo){
        Empleado empleado = empleadoser.findEmpleado(id);
        modelo.addAttribute("empleado", empleado);
        
        return "editar_empleado";
    }
    
   @PostMapping("empleado/editar/{id}")
    public String editarEmpleado(@PathVariable int id, @ModelAttribute("empleado")Empleado emp, Model modelo){
        System.out.println(emp.getFechaIngreso());
        System.out.println(emp.getFechaNacimiento());
        empleadoser.updateEmpleado(emp);
        return "redirect:/empleado/ver";

    }
    @PostMapping("empleado/baja/")
    public String mostrarBajaEmpleado(@RequestParam int id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaBaja){
        System.out.println(id+" "+fechaBaja);
        Empleado emp = empleadoser.findEmpleado(id);
        emp.setFechaBaja(fechaBaja);
        empleadoser.bajaEmpleado(id);
    return "redirect:/empleado/ver";
    }
    
    @GetMapping("empleado/detalle/{id}")
    public String detalleEmpleado(@PathVariable int id, Model modelo){
        Empleado empleado = empleadoser.findEmpleado(id);
        modelo.addAttribute("empleado", empleado);
    return "ver_empleado";
    }
    
    @GetMapping("empleado/domicilio/ver/{id}")
    public String domiciliosEmpleado(@PathVariable int id, Model modelo){
        Empleado emp=empleadoser.findEmpleado(id);
        List<Domicilio> domicilios= emp.getDomicilios();
        modelo.addAttribute("domicilios", domicilios);
        return "ver_domicilios_empleado";
    }
}
