package com.overnet.project_sanatorio.controller;

import com.overnet.project_sanatorio.model.DetalleParteDiario;
import com.overnet.project_sanatorio.service.IParteDiarioService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.overnet.project_sanatorio.model.ParteDiario;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class ParteDiarioController {
    @Autowired
    private IParteDiarioService pdser;
    
    @GetMapping("/partediario")
    public String mostrarParteDiario(@RequestParam(name = "fecha", required = false) LocalDate fecha, Model modelo){
        if(fecha!=null){    
            ParteDiario pdiario = pdser.findByDate(fecha);
            modelo.addAttribute("partediario", pdiario);
            modelo.addAttribute("fecha", fecha);
        }
        return "parte_diario";
    }
    
    @PostMapping("/partediario/generar")
    public String generarParteDiario(@RequestParam("fecha") LocalDate fecha){
        ParteDiario pd = new ParteDiario();
        pd.setFechaEmision(fecha);
        pdser.generate(pd);
        
        return "redirect:/partediario?fecha=" + fecha;
    }
    
    @GetMapping("/partediario/{id}/contratos")
    public String mostrarContratos(@PathVariable("id")int id, Model modelo){
        ParteDiario pd = pdser.findById(id);
        DetalleParteDiario dpd = pdser.findByIdParteDiarioAndNombreSector(id, "Finalizaciones de contratos");
        modelo.addAttribute("contratos", dpd.getContratos());
        modelo.addAttribute("partediario", pd);
        return "parte_diario_contratos";
    }
    @GetMapping("/partediario/{id}/licencias")
    public String mostrarLicencias(@PathVariable("id")int id, Model modelo){
        ParteDiario pd = pdser.findById(id);
        DetalleParteDiario dpd = pdser.findByIdParteDiarioAndNombreSector(id, "Finalizaciones de licencias");
        modelo.addAttribute("licencias", dpd.getLicTomadas());
        modelo.addAttribute("partediario", pd);
        return "parte_diario_licencias";
    }
    @GetMapping("/partediario/{id}/jubilaciones")
    public String mostrarJubilaciones(@PathVariable("id")int id, Model modelo){
        ParteDiario pd = pdser.findById(id);
        DetalleParteDiario dpd = pdser.findByIdParteDiarioAndNombreSector(id, "Jubilaciones");
        modelo.addAttribute("jubilaciones", dpd.getEmpleados());
        modelo.addAttribute("partediario", pd);
        return "parte_diario_jubilaciones";
    }
    @GetMapping("/partediario/{id}/inasistencias")
    public String mostrarInasistencias(@PathVariable("id")int id, Model modelo){
        ParteDiario pd = pdser.findById(id);
        DetalleParteDiario dpd = pdser.findByIdParteDiarioAndNombreSector(id, "Inasistencias");
        modelo.addAttribute("inasistencias", dpd.getInasistencias());
        modelo.addAttribute("partediario", pd);
        return "parte_diario_inasistencias";
    }
}
