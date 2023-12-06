package com.overnet.project_sanatorio.controller;

import com.overnet.project_sanatorio.dto.TipoLicenciaDTO;
import com.overnet.project_sanatorio.model.Contrato;
import com.overnet.project_sanatorio.model.LicenciaTomada;
import com.overnet.project_sanatorio.service.IEmpleadoService;
import com.overnet.project_sanatorio.service.ILicenciaTomadaService;
import jakarta.servlet.http.HttpServletRequest;
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
public class LicenciaTomadaController {
    @Autowired
    private ILicenciaTomadaService lictomser;
    @Autowired 
    private IEmpleadoService empleadoser;
    @GetMapping("/empleado/licencia/alta/{idEmpleado}")
    public String mostrarFormAltaDesdeEmpleado(Model modelo, @PathVariable int idEmpleado){
        List<TipoLicenciaDTO>dtos=lictomser.armarTipoLicenciaDTO(idEmpleado);
        LicenciaTomada licencia = new LicenciaTomada();
        modelo.addAttribute("licencia", licencia);
        modelo.addAttribute("dtos", dtos);

        return "alta_licencias_emp";
    }
    
    @PostMapping("/empleado/licencia/alta/")
    public String altaLicenciaDesdeEmpleado(@ModelAttribute("licencia")LicenciaTomada licencia, @RequestParam("idEmpleado") int idEmpleado, @RequestParam("idTipo")int idTipo){
        licencia.setEmpleado(empleadoser.findEmpleado(idEmpleado));
        licencia.setTipoLicencia(lictomser.findById(idTipo));
        lictomser.saveLicenciaTomada(licencia);
        return "redirect:/empleado/licencia/ver/"+idEmpleado; 
    }
    
    @GetMapping("/empleado/licencia/alta/cancelar/{id}")
    public String volverAListaLicencias(@PathVariable int id){
        return "redirect:/empleado/licencia/ver/"+id;
    }
    
    @GetMapping("/licencia/editar/{id}")
    public String mostrarFormEditar(@PathVariable int id, Model modelo, @RequestParam int idEmpleado){
        LicenciaTomada lic = lictomser.findLicenciaTomadaById(id);
        System.out.println("FECHA DESDE: "+lic.getFechaDesde());
        System.out.println("FECHA HASTA: "+lic.getFechaHasta());
        List<TipoLicenciaDTO> dtos = lictomser.armarTipoLicenciaDTO(idEmpleado);
        modelo.addAttribute("licencia", lic);
        modelo.addAttribute("idEmpleado", idEmpleado);
        modelo.addAttribute("dtos", dtos);
        return "editar_licencias_emp";
    }
    
    @PostMapping("/licencia/editar/{id}")
    public String editarLicencia (@PathVariable int id, @ModelAttribute("licencia")LicenciaTomada lic, @ModelAttribute("idEmpleado")int idEmpleado, Model modelo){
        LicenciaTomada l = lictomser.findLicenciaTomadaById(id);
        l.setCertificado(lic.isCertificado());
        l.setDescripcion(lic.getDescripcion());
        l.setFechaDesde(lic.getFechaDesde());
        l.setFechaHasta(lic.getFechaHasta());
        l.setTerminada(lic.isTerminada());
        lictomser.updateLicenciaTomada(l);
       
        return "redirect:/empleado/licencia/ver/"+idEmpleado;
    }
    
    @GetMapping("empleado/licencia/ver/detalle/{id}")
    public String detalleLicenciaDesdeEmp(@PathVariable int id, Model modelo){
        LicenciaTomada l = lictomser.findLicenciaTomadaById(id);
        modelo.addAttribute("licencia", l);
        return "ver_licencia_emp";
    }
    
    
    @GetMapping("/licencia/detalle/{id}")
    public String detalleLicencia(@PathVariable int id, Model modelo){
        LicenciaTomada l = lictomser.findLicenciaTomadaById(id);
        modelo.addAttribute("licencia", l);
        return "ver_licencia";
    }
    
    @GetMapping("/licencia/lista")
    public String traerLicencias (Model modelo, @Param("palabra")String palabra, @Param("terminada")boolean terminada){

        if (palabra == null) {
            palabra = ""; //
        }
        System.out.println(!terminada);
        List<LicenciaTomada>lic = lictomser.getLicenciasTomadas(palabra, terminada);
        modelo.addAttribute("licencias", lic);
        modelo.addAttribute("palabra", palabra);
        modelo.addAttribute("terminada", terminada);
       
    return "lista_licenciastomadas";
    }
    
    public String calcularDiasRestantes(List<TipoLicenciaDTO> dtos, int idDto) {
    TipoLicenciaDTO tipolic=null;
        for(TipoLicenciaDTO t : dtos){
            if(t.getIdTipo()==idDto){
                tipolic=t;
            }
        }

        if (tipolic != null && tipolic.getDiasTotales()!=null) {
            return String.valueOf(tipolic.getDiasTotales()- tipolic.getDiasTomados());
        } else {
            return "";
        }
    }
    
    @PostMapping("/licencia/baja/")
    public String eliminarLicencia(@RequestParam int id, HttpServletRequest request){
        lictomser.deleteLicenciaTomada(id);
        String paginaAnterior = request.getHeader("Referer"); // Obtiene la URL de la página anterior desde la cabecera Referer
        if (paginaAnterior != null && !paginaAnterior.isEmpty()) {
        return "redirect:" + paginaAnterior;
    } else {
        return "redirect:/empleado/ver"; 
    }
    }
}
