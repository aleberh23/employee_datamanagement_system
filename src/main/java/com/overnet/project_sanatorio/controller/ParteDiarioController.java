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
    public String mostrarParteDiario(@RequestParam(name = "fecha", required = false) LocalDate fecha, Model modelo) {
        ParteDiario pdiario = pdser.findByDate(fecha);

        if (pdiario == null) {
            modelo.addAttribute("encontrado", false);
            modelo.addAttribute("fecha", fecha);
        } else {
            pdser.updateParteDiario(pdiario.getId());

            // Buscar los detalles del parte diario
            DetalleParteDiario dpdcontratos = pdser.findByIdParteDiarioAndNombreSector(pdiario.getId(), "Contratos");
            DetalleParteDiario dpdlicencias = pdser.findByIdParteDiarioAndNombreSector(pdiario.getId(), "Licencias");
            DetalleParteDiario dpdjubilaciones = pdser.findByIdParteDiarioAndNombreSector(pdiario.getId(), "Jubilaciones");
            DetalleParteDiario dpdinasistencias = pdser.findByIdParteDiarioAndNombreSector(pdiario.getId(), "Inasistencias");

            // Agregar los detalles al modelo
            modelo.addAttribute("dpdlicencias", dpdlicencias);
            modelo.addAttribute("dpdcontratos", dpdcontratos);
            modelo.addAttribute("dpdjubilaciones", dpdjubilaciones);
            modelo.addAttribute("dpdinasistencias", dpdinasistencias);
            
           //agregar fecha al modelo
           modelo.addAttribute("fecha", fecha);
           modelo.addAttribute("encontrado", true);
        }

        // Agregar el parte diario al modelo
        modelo.addAttribute("partediario", pdiario);
        return "parte_diario";
    }
    
    @PostMapping("/partediario/generar")
    public String generarParteDiario(@RequestParam("fecha") LocalDate fecha) {
        ParteDiario pdiario = pdser.findByDate(fecha);

        if (pdiario == null) {
            // Si el parte diario no existe, generarlo
            pdiario = new ParteDiario();
            pdiario.setFechaEmision(fecha);
            pdser.generate(pdiario);
        } else {
            // Si el parte diario existe, actualizar si es necesario
            pdser.updateParteDiario(pdiario.getId());
        }

        return "redirect:/partediario?fecha=" + fecha;
    }
    
    @GetMapping("/partediario/{id}/contratos")
    public String mostrarContratos(@PathVariable("id")int id, Model modelo){
        ParteDiario pd = pdser.findById(id);
        DetalleParteDiario dpd = pdser.findByIdParteDiarioAndNombreSector(id, "Contratos");
        modelo.addAttribute("contratos", dpd.getContratos());
        modelo.addAttribute("partediario", pd);
        return "parte_diario_contratos";
    }
    @GetMapping("/partediario/{id}/licencias")
    public String mostrarLicencias(@PathVariable("id")int id, Model modelo){
        ParteDiario pd = pdser.findById(id);
        DetalleParteDiario dpd = pdser.findByIdParteDiarioAndNombreSector(id, "Licencias");
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
    
    @PostMapping("/partediario/{id}/actualizar")
    public String actualizarParteDiario(@PathVariable("id")int id, Model modelo){
        ParteDiario pd = pdser.findById(id);
        pdser.updateParteDiario(id);
        return "redirect:/partediario?fecha="+pd.getFechaEmision();
    }
}
