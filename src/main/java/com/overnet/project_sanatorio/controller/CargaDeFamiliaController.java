package com.overnet.project_sanatorio.controller;

import com.overnet.project_sanatorio.model.CargaDeFamilia;
import com.overnet.project_sanatorio.model.Domicilio;
import com.overnet.project_sanatorio.service.EmpleadoService;
import com.overnet.project_sanatorio.service.ICargaDeFamiliaService;
import jakarta.servlet.http.HttpServletRequest;
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
public class CargaDeFamiliaController {
    @Autowired
    private ICargaDeFamiliaService cargaser;
    @Autowired
    private EmpleadoService empleadoser;
    
    @GetMapping("/empleado/cargaDeFamilia/alta/{idEmpleado}")
    public String mostrarFormAltaDesdeEmpleado(Model modelo, @PathVariable int idEmpleado){
        CargaDeFamilia c = new CargaDeFamilia();
        modelo.addAttribute("cargadefamilia", c);
        return "alta_cargasdefamilia_emp";
    }
    @PostMapping("/empleado/cargaDeFamilia/alta/")
    public String altaCargaDeFamiliaDesdeEmpleado(@ModelAttribute("cargadefamilia")CargaDeFamilia car, @RequestParam("idEmpleado") int idEmpleado){
        car.setEmpleado(empleadoser.findEmpleado(idEmpleado));
        cargaser.saveCargaDeFamilia(car);
        return "redirect:/empleado/cargaDeFamilia/ver/"+idEmpleado;
    }
    
    
    @PostMapping("cargaDeFamilia/baja/")
    public String bajaCargaDeFamilia(@RequestParam int id, HttpServletRequest request){
        cargaser.bajaCargaDeFamilia(id);
        String paginaAnterior = request.getHeader("Referer"); // Obtiene la URL de la página anterior desde la cabecera Referer
        if (paginaAnterior != null && !paginaAnterior.isEmpty()) {
        return "redirect:" + paginaAnterior;
    } else {
        return "redirect:/empleado/ver"; 
    }
    }
    @GetMapping("/empleado/cargaDeFamilia/alta/cancelar/{id}")
    public String volverAListaCargasDeFamilia(@PathVariable int id) {
        return "redirect:/empleado/cargaDeFamilia/ver/"+id;
    }
        
    @GetMapping("cargaDeFamilia/editar/{id}")
    public String mostrarFormEditar(@PathVariable int id, Model modelo, @RequestParam int idEmpleado){
        CargaDeFamilia car = cargaser.findCargaDeFamila(id);
        modelo.addAttribute("cargadefamilia", car);
        modelo.addAttribute("idEmpleado", idEmpleado);
        return "editar_cargadefamilia_emp";
    }
    
   @PostMapping("cargaDeFamilia/editar/{id}")
    public String editarCargaDeFamilia(@PathVariable int id, @ModelAttribute("cargadefamilia")CargaDeFamilia car, @ModelAttribute("idEmpleado")int idEmpleado, Model modelo){
        System.out.println("Entro");
        CargaDeFamilia c = cargaser.findCargaDeFamila(id);
        c.setId(car.getId());
        c.setNombre(car.getNombre());
        c.setApellido(car.getApellido());
        c.setVinculo(car.getVinculo());
        c.setTipoDoc(car.getTipoDoc());
        c.setNroDoc(car.getNroDoc());
        c.setBaja(car.isBaja());
        cargaser.updateCargaDeFamilia(c);
        return"redirect:/empleado/cargaDeFamilia/ver/"+idEmpleado;
       
    }
    
    
}
