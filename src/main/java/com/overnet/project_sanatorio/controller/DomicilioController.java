package com.overnet.project_sanatorio.controller;

import com.overnet.project_sanatorio.model.Domicilio;
import com.overnet.project_sanatorio.model.Empleado;
import com.overnet.project_sanatorio.service.DomicilioService;
import com.overnet.project_sanatorio.service.EmpleadoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DomicilioController {
    
    @Autowired
    DomicilioService domicilioser;
    @Autowired
    EmpleadoService empleadoser;
    
    @GetMapping("/empleado/domicilio/alta/{idEmpleado}")
    public String mostrarFormAltaDesdeEmpleado(Model modelo, @PathVariable int idEmpleado){
        Domicilio dom = new Domicilio();
        modelo.addAttribute("domicilio", dom);
        return "alta_domicilios_emp";
    }
    @PostMapping("/empleado/domicilio/alta/")
    public String altaDomicilioDesdeEmpleado(@ModelAttribute("domicilio")Domicilio dom, @RequestParam("idEmpleado") int idEmpleado){
        dom.setEmpleado(empleadoser.findEmpleado(idEmpleado));
        domicilioser.saveDomicilio(dom);
        return "redirect:/empleado/domicilio/ver/"+idEmpleado;
    }
    
    
    @PostMapping("domicilio/baja/")
    public String bajaDomicilio(@RequestParam int id, HttpServletRequest request){
        domicilioser.bajaDomicilio(id);
        String paginaAnterior = request.getHeader("Referer"); // Obtiene la URL de la página anterior desde la cabecera Referer
        if (paginaAnterior != null && !paginaAnterior.isEmpty()) {
        return "redirect:" + paginaAnterior;
    } else {
        return "redirect:/empleado/ver"; 
    }
    }
    @GetMapping("/empleado/domicilio/alta/cancelar/{id}")
    public String volverAListaDomicilios(@PathVariable int id) {
        return "redirect:/empleado/domicilio/ver/"+id;
    }
        
    @GetMapping("domicilio/editar/{id}")
    public String mostrarFormEditar(@PathVariable int id, Model modelo){
        Domicilio domicilio = domicilioser.findDocimicilio(id);
        modelo.addAttribute("domicilio", domicilio);
        
        return "editar_domicilio_emp";
    }
    
   @PostMapping("domicilio/editar/{id}")
    public String editarDomicilio(@PathVariable int id, @ModelAttribute("domicilio")Domicilio domicilio, Model modelo, @RequestParam int idEmpleado){
        System.out.println("Entro");
        Domicilio dom = domicilioser.findDocimicilio(id);
        dom.setId(domicilio.getId());
        dom.setCalle(domicilio.getCalle());
        dom.setNumero(domicilio.getNumero());
        dom.setLocalidad(domicilio.getLocalidad());
        dom.setAuditoriaMedica(domicilio.isAuditoriaMedica());
        dom.setBaja(domicilio.isBaja());
        domicilioser.updateDomicilio(dom);
        return"redirect:/empleado/domicilio/ver/"+idEmpleado;
       
    }
}
