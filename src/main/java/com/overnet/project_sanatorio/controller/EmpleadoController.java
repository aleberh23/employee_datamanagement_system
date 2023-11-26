package com.overnet.project_sanatorio.controller;

import com.overnet.project_sanatorio.model.CargaDeFamilia;
import com.overnet.project_sanatorio.model.Domicilio;
import com.overnet.project_sanatorio.model.Empleado;
import com.overnet.project_sanatorio.model.Sector;
import com.overnet.project_sanatorio.service.IDomicilioService;
import com.overnet.project_sanatorio.service.IEmpleadoService;
import com.overnet.project_sanatorio.service.ISectorService;
import java.time.LocalDate;
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
    private IEmpleadoService empleadoser;
    @Autowired
    private IDomicilioService domicilioser;
    @Autowired
    private ISectorService sectorser;
    
    
   @GetMapping("/empleado/alta")
    public String mostrarFormAltaEmpleado(Model modelo){
        Empleado emp = new Empleado();
        Domicilio dom = new Domicilio();
        List<Sector> sectores= sectorser.getSectores();
        modelo.addAttribute("empleado", emp);
        modelo.addAttribute("domicilio", dom);
        modelo.addAttribute("sectores", sectores);
        return "alta_empleado";
    }
    @PostMapping("/empleado/alta")
    public String guardarEmpleado(@ModelAttribute("empleado")Empleado emp, @ModelAttribute("domicilio")Domicilio dom, @RequestParam("sectorId") int sectorId){
    emp.setSector(sectorser.findSectorById(sectorId));
    dom.setEmpleado(emp);
    empleadoser.saveEmpleado(emp);
    domicilioser.saveDomicilio(dom);
    return "redirect:/empleado/alta";
    }
    @GetMapping("empleado/ver")
    public String traerEmpleados(Model modelo, @Param("palabra")String palabra, @Param("deBaja")boolean deBaja){

        if (palabra == null) {
            palabra = ""; //
        }
        System.out.println(!deBaja);
        List<Empleado> empleados = empleadoser.getEmpleados(palabra, deBaja);
        modelo.addAttribute("empleados", empleados);
        modelo.addAttribute("palabra", palabra);
        modelo.addAttribute("deBaja", deBaja);
       
    return "vista_empleados";
    }
    
    @GetMapping("empleado/editar/{id}")
    public String mostrarFormEditar(@PathVariable int id, Model modelo){
        Empleado empleado = empleadoser.findEmpleado(id);
        List<Sector> sectores = sectorser.getSectores();
        modelo.addAttribute("sectores", sectores);
        modelo.addAttribute("empleado", empleado);
        
        return "editar_empleado";
    }
    
   @PostMapping("empleado/editar/{id}")
    public String editarEmpleado(@PathVariable int id, @ModelAttribute("empleado")Empleado emp, Model modelo, @RequestParam("sectorId")int sectorId){
        System.out.println(emp.getFechaIngreso());
        System.out.println(emp.getFechaNacimiento());
        Sector s = sectorser.findSectorById(sectorId);
        emp.setSector(s);
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
        modelo.addAttribute("idEmpleado", id);
        return "ver_domicilios_empleado";
    }
    
    @GetMapping("empleado/cargaDeFamilia/ver/{id}")
    public String cargasDeFamiliaEmpleado(@PathVariable int id, Model modelo){
        Empleado emp=empleadoser.findEmpleado(id);
        List<CargaDeFamilia> cargasdefamilia= emp.getCargasDeFamilia();
        modelo.addAttribute("cargasdefamilia", cargasdefamilia);
        modelo.addAttribute("idEmpleado", id);
        return "ver_cargasdefamilia_empleado";
    }
}
