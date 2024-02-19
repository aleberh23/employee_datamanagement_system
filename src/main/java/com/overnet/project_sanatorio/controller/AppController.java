package com.overnet.project_sanatorio.controller;

import com.overnet.project_sanatorio.service.IContratoService;
import com.overnet.project_sanatorio.service.IEmpleadoService;
import com.overnet.project_sanatorio.service.ILicenciaTomadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AppController {
    
    @Autowired
    private IEmpleadoService empser;
    
    @Autowired
    private IContratoService contser;
    
    @Autowired
    private ILicenciaTomadaService lictomser;
    
     @GetMapping("/")
    public String menu(Model modelo) {
        int cantidadEmpleados = empser.countEmpleados();
        int cantidadActivos = empser.countEmpleadosActivos();
        int cantidadNoActivos = empser.countEmpleadosNoActivos();
        int cantidadContratosActivos = contser.countContratosActivos();
        int cantidadLicencias = lictomser.countLicenciasActivas();
        int cantidadEnfermeria = empser.countEmpleadosPorSector("Enfermeria");
        int cantidadAdministracion = empser.countEmpleadosPorSector("Administracion");
        int cantidadCamilleria = empser.countEmpleadosPorSector("Camilleria");
        int cantidadLimpieza = empser.countEmpleadosPorSector("Limpieza");
        int cantidadRecepcion = empser.countEmpleadosPorSector("Recepcion");
        
        modelo.addAttribute("cantidadEmpleados", cantidadEmpleados);
        modelo.addAttribute("cantidadActivos", cantidadActivos);
        modelo.addAttribute("cantidadNoActivos", cantidadNoActivos);
        modelo.addAttribute("cantidadContratosActivos", cantidadContratosActivos);
        modelo.addAttribute("cantidadLicencias", cantidadLicencias);
        modelo.addAttribute("cantidadEnfermeria", cantidadEnfermeria);
        modelo.addAttribute("cantidadAdministracion", cantidadAdministracion);
        modelo.addAttribute("cantidadCamilleria", cantidadCamilleria);
        modelo.addAttribute("cantidadLimpieza", cantidadLimpieza);
        modelo.addAttribute("cantidadRecepcion", cantidadRecepcion);
        
        return "index";
    }
    @GetMapping("/login")
    public String login(){
    return "login";
    }
   
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }
}
