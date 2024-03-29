package com.overnet.project_sanatorio.controller;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.overnet.project_sanatorio.model.Ausencia;
import com.overnet.project_sanatorio.model.Empleado;
import com.overnet.project_sanatorio.model.ParteDiario;
import com.overnet.project_sanatorio.model.TipoInasistencia;
import com.overnet.project_sanatorio.service.IAusenciaService;
import com.overnet.project_sanatorio.service.IEmpleadoService;
import com.overnet.project_sanatorio.service.IInasistenciaService;
import com.overnet.project_sanatorio.service.IParteDiarioService;
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
public class AusenciaController {
    @Autowired
    private IAusenciaService auser;
    @Autowired
    private IInasistenciaService inasiser;
    @Autowired
    private IEmpleadoService empser;
    @Autowired
    private IParteDiarioService pdser;
    
    @GetMapping("/ausencia/alta/empleado")
    public String mostrarSeleccionEmpleado(Model modelo,@RequestParam(value = "idAusencia", required = false) Integer idAusencia, @Param("filtro") String filtro){
        if (filtro == null) {
           filtro = ""; 
        }
        // Agregar idAusencia al modelo
        modelo.addAttribute("idAusencia", idAusencia);
        
        List<Empleado>emp = empser.getEmpleadosByNombreApellidoNroLeg(filtro.toLowerCase());
        modelo.addAttribute("empleados", emp);
    return "seleccion_empleado";
    }
    
    @GetMapping("/ausencia/alta")
    public String mostrarFormAlta(@RequestParam(value = "idEmpleado", required = false) Integer idEmpleado, Model modelo){
     Ausencia a = new Ausencia();
     List<TipoInasistencia>tiposinasistencia = auser.findAllTiposInasistencia();
     List<String>partesd = pdser.getPartesDiarioForAusencia(LocalDate.now());
     System.out.println("lista vacia:"+partesd.isEmpty());
     Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
     String partes = gson.toJson(partesd);
     modelo.addAttribute("ausencia", a);
     modelo.addAttribute("partes", partes);
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
        modelo.addAttribute("filtro", filtro.toLowerCase());
         
        return "lista_ausencias";
    }
    
    @GetMapping("/ausencia/editar/{id}")
    public String mostrarFormEditar(@PathVariable int id, @RequestParam(value = "idEmpleado", required = false) Integer idEmpleado, Model modelo){
        Ausencia ausencia = auser.findAusenciaById(id);
        List<TipoInasistencia>tiposinasistencia = auser.findAllTiposInasistencia();
        List<String>partesd = pdser.getPartesDiarioForAusencia(ausencia.getFecha());
        System.out.println("lista vacia:"+partesd.isEmpty());
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        String partes = gson.toJson(partesd);
        modelo.addAttribute("partes", partes);
        modelo.addAttribute("ausencia", ausencia);
        modelo.addAttribute("fechaOriginal", ausencia.getFecha());
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
        
        return "editar_ausencia";
    }
    
    @PostMapping("/ausencia/editar/{id}")
    public String procesarFormEditar(@PathVariable int id, @ModelAttribute("ausencia")Ausencia aus, @RequestParam("empleadoId") int empleadoId, @RequestParam("tipoInasistenciaId")int tipoInasistenciaId, Model modelo){
        Empleado empleado = empser.findEmpleado(empleadoId);
        TipoInasistencia t = auser.findTipoInasistenciaById(tipoInasistenciaId);
        aus.setEmpleado(empleado);
        aus.setTipoInasistencia(t);
        auser.updateAusencia(aus);
        
        return "redirect:/ausencia/lista"; 
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
