package com.overnet.project_sanatorio.controller;

import com.overnet.project_sanatorio.model.Contrato;
import com.overnet.project_sanatorio.model.Empleado;
import com.overnet.project_sanatorio.service.IContratoService;
import com.overnet.project_sanatorio.service.IEmpleadoService;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
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
public class ContratoController {
    @Autowired
    private IContratoService contratoser;
    @Autowired
    private IEmpleadoService empleadoser;
    
    @GetMapping("/empleado/contrato/alta/{idEmpleado}")
    public String mostrarFormAltaDesdeEmpleado(Model modelo, @PathVariable int idEmpleado, @RequestParam(required = false)Integer idSuperpuesto){
        Contrato con = new Contrato();
        con.setBaja(false);
        Empleado emp = empleadoser.findEmpleado(idEmpleado);
        LocalDate ultimaFechaFin = contratoser.obtenerUltimaFechaFin(idEmpleado); 
        if(emp.getContratos().isEmpty()){
            con.setFechaInicio(emp.getFechaIngreso());
        } else if(ultimaFechaFin!=null){
            con.setFechaInicio(ultimaFechaFin.plusDays(1));            
        }else{
            con.setFechaInicio(emp.getFechaIngreso());
        }
        
               
        modelo.addAttribute("contrato", con);
        modelo.addAttribute("empleado", emp);
        if (!emp.getContratos().isEmpty()){
            modelo.addAttribute("ultimaFechaFin", ultimaFechaFin);
        }else{
            modelo.addAttribute("ultimaFechaFin", ultimaFechaFin);
        }
        if(idSuperpuesto!=null){
            Contrato superpuesto = contratoser.findContrato(idSuperpuesto);
            modelo.addAttribute("superpuesto", superpuesto);
        }
        return "alta_contratos_emp";
    }
    
    @PostMapping("/empleado/contrato/alta/")
    public String altaContratoDesdeEmpleado(@ModelAttribute("contrato")Contrato con, @RequestParam("idEmpleado") int idEmpleado){
        if(contratoser.findContratoSupepuesto(idEmpleado, con)==null){   
            con.setEmpleado(empleadoser.findEmpleado(idEmpleado));
            contratoser.saveContrato(con);
            return "redirect:/empleado/contrato/ver/"+idEmpleado; 
        }else{
            Contrato superpuesto = contratoser.findContratoSupepuesto(idEmpleado, con);
            return "redirect:/empleado/contrato/alta/"+idEmpleado+"?idSuperpuesto="+superpuesto.getIdContrato();
        }
    }
    
    @PostMapping("/contrato/baja/")
    public String bajaContrato(@RequestParam int id, HttpServletRequest request){
        contratoser.bajaContrato(id);
        String paginaAnterior = request.getHeader("Referer"); // Obtiene la URL de la página anterior desde la cabecera Referer
        if (paginaAnterior != null && !paginaAnterior.isEmpty()) {
        return "redirect:" + paginaAnterior;
    } else {
        return "redirect:/empleado/ver"; 
    }
    }
    
    @GetMapping("/empleado/contrato/alta/cancelar/{id}")
    public String volverAListaContratos(@PathVariable int id){
        return "redirect:/empleado/contrato/ver/"+id;
    }
    
    @GetMapping("/contrato/editar/{id}")
    public String mostrarFormEditar(@PathVariable int id, Model modelo, @RequestParam(required = false)Integer idSuperpuesto){
        Contrato con = contratoser.findContrato(id);;
        System.out.println("con id="+con.getIdContrato());
        LocalDate ultimaFechaFin = contratoser.obtenerUltimaFechaFinExceptuando(con.getEmpleado().getId(), con.getIdContrato());
        modelo.addAttribute("contrato", con);
        modelo.addAttribute("ultimaFechaFin", ultimaFechaFin);
        if(idSuperpuesto!=null){
            Contrato superpuesto = contratoser.findContrato(idSuperpuesto);
            System.out.println("superpuesto id: "+superpuesto.getIdContrato());
            modelo.addAttribute("superpuesto", superpuesto);
            
        }
        return "editar_contrato_emp";
    }
    
    @PostMapping("/contrato/editar/{id}")
    public String editarContrato(@PathVariable int id, @ModelAttribute("contrato")Contrato con, Model modelo){
        Contrato c = contratoser.findContrato(id);
        c.setIdContrato(id);
        c.setFechaInicio(con.getFechaInicio());
        c.setFechaFin(con.getFechaFin());
        c.setDescripcion(con.getDescripcion());
        if(contratoser.findContratoSupepuestoExcluyendose(c.getEmpleado().getId(), c)==null){                            
            contratoser.updateContrato(c);
            return "redirect:/empleado/contrato/ver/"+c.getEmpleado().getId();
        }else{
            Contrato superpuesto=contratoser.findContratoSupepuestoExcluyendose(c.getEmpleado().getId(), c);
            return "redirect:/contrato/editar/"+c.getIdContrato()+"?idSuperpuesto="+superpuesto.getIdContrato();
        }
    }
    
    @GetMapping("/contrato/lista")
    public String traerContratos (Model modelo, @Param("palabra")String palabra, @Param("deBaja")boolean deBaja){

        if (palabra == null) {
            palabra = ""; //
        }
        System.out.println(!deBaja);
        List<Contrato> contratos = contratoser.findBySearch(palabra, deBaja);
        modelo.addAttribute("contratos", contratos);
        modelo.addAttribute("palabra", palabra.toLowerCase());
        modelo.addAttribute("deBaja", deBaja);
       
    return "lista_contratos";
    }
    
    @GetMapping("/contrato/detalle/{id}")
    public String verContrato(@PathVariable int id, Model modelo){
        Contrato c = contratoser.findContrato(id);
        modelo.addAttribute("contrato", c);
        return "ver_contrato";
    }
    
    
    @GetMapping("/empleado/contrato/ver/detalle/{id}")
    public String verContratoDesdeEmpleado(@PathVariable int id, Model modelo){
        Contrato c = contratoser.findContrato(id);
        modelo.addAttribute("contrato", c);
        return "ver_contrato_emp";
    }
    

}
