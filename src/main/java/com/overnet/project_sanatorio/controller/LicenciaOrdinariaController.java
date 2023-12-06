package com.overnet.project_sanatorio.controller;

import com.overnet.project_sanatorio.dto.LicenciaOrdinariaDTO;
import com.overnet.project_sanatorio.service.IEmpleadoService;
import com.overnet.project_sanatorio.service.ILicenciaOridinariaService;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LicenciaOrdinariaController {
    @Autowired
    public IEmpleadoService empleadoser;
    @Autowired
    public ILicenciaOridinariaService licordser;
    
    
    @GetMapping("/licenciasOrdinaria/generar")
    public String mostrarFormGenerar(Model modelo, @RequestParam(required = false) String exito){
        HashMap<Integer, LicenciaOrdinariaDTO>ldtos=licordser.crearDtos();
        modelo.addAttribute("ldtos", ldtos);
        modelo.addAttribute("exito", exito);
        return "generar_licencias_ordinarias";
    }
    
    @PostMapping("/licenciasOrdinarias/generar/")
    public String generarLicencias(@RequestParam List<Integer> seleccionados, Model modelo) {
        HashMap<Integer, LicenciaOrdinariaDTO> ldtos = licordser.crearDtos();
        //modelo.addAttribute("ldtos", ldtos);
        seleccionados.forEach(s ->{
            licordser.guardarLicOrd(ldtos.get(s));
        });

        return "redirect:/licenciasOrdinaria/generar?exito";  
    }
}
