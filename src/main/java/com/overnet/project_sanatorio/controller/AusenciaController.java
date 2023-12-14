package com.overnet.project_sanatorio.controller;

import com.overnet.project_sanatorio.model.Ausencia;
import com.overnet.project_sanatorio.model.Empleado;
import com.overnet.project_sanatorio.model.TipoInasistencia;
import com.overnet.project_sanatorio.service.IAusenciaService;
import com.overnet.project_sanatorio.service.IEmpleadoService;
import com.overnet.project_sanatorio.service.IInasistenciaService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AusenciaController {
    @Autowired
    private IAusenciaService auser;
    @Autowired
    private IInasistenciaService inasiser;
    @Autowired
    private IEmpleadoService empser;
    
    @GetMapping("/ausencia/alta/empleado")
    public String mostrarSeleccionEmpleado(Model modelo, @Param("filtro") String filtro){
        if (filtro == null) {
           filtro = ""; 
        }
        
        List<Empleado>emp = empser.getEmpleadosByNombreApellidoNroLeg(filtro);
        modelo.addAttribute("empleados", emp);
    return "seleccion_empleado";
    }
    
    @GetMapping("/ausencia/alta")
    public String mostrarFormAlta(@RequestParam(value = "idEmpleado", required = false) Integer idEmpleado, Model modelo){
     Ausencia a = new Ausencia();
     List<TipoInasistencia>tiposinasistencia = auser.findAllTiposInasistencia();
     modelo.addAttribute("ausencia", a);
     modelo.addAttribute("tiposinasistencia", tiposinasistencia);
     if (idEmpleado == null) {
            modelo.addAttribute("empleado", null);
            System.out.println("ID EMPLEADO IS NULL");
        } else {
            Empleado emp = empser.findEmpleado(idEmpleado);
            System.out.println("ID EMPLADO IS: "+idEmpleado);
            System.out.println(emp.toString());
            modelo.addAttribute("empleado", emp);
        }
    return "alta_ausencia";
    }

    @PostMapping("/ausencia/alta")
    public String procesarFormAlta(@ModelAttribute("ausencia") Ausencia ausencia,@RequestParam("empleadoId") int empleadoId, @RequestParam("tipoInasistenciaId")int tipoInasistenciaId, Model modelo) {
        Empleado empleado = empser.findEmpleado(empleadoId);
        TipoInasistencia t = auser.findTipoInasistenciaById(tipoInasistenciaId);
        System.out.println(empleado.getNombre()+" "+empleado.getApellido());
        ausencia.setEmpleado(empleado);
        ausencia.setTipoInasistencia(t);
        auser.saveAusencia(ausencia);
        return "redirect:/ausencia/alta";
    }
    
    @GetMapping("/ausencia/lista")
    public String mostrarLista(Model modelo, @Param("filtro") String filtro){
        if (filtro == null) {
            filtro = ""; //
        }
        List<Ausencia>ausencias=auser.getAusenciasByFilter(filtro);
        modelo.addAttribute("ausencias", ausencias);
        modelo.addAttribute("filtro", filtro);
         
        return "lista_ausencias";
    }
    
    @PostMapping("/ausencia/baja/")
    public String eliminarAusencia(@RequestParam int id, HttpServletRequest request){
        auser.deleteAusencia(id);
        String paginaAnterior = request.getHeader("Referer"); // Obtiene la URL de la página anterior desde la cabecera Referer
        if (paginaAnterior != null && !paginaAnterior.isEmpty()) {
        return "redirect:" + paginaAnterior;
    } else {
        return "redirect:/ausencia/lista"; 
    }
    }
}
