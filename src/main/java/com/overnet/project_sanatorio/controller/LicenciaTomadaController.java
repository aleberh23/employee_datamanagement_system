package com.overnet.project_sanatorio.controller;

import com.overnet.project_sanatorio.dto.TipoLicenciaDTO;
import com.overnet.project_sanatorio.model.Contrato;
import com.overnet.project_sanatorio.model.Empleado;
import com.overnet.project_sanatorio.model.Inasistencia;
import com.overnet.project_sanatorio.model.LicenciaOrdinaria;
import com.overnet.project_sanatorio.model.LicenciaTomada;
import com.overnet.project_sanatorio.service.IEmpleadoService;
import com.overnet.project_sanatorio.service.IInasistenciaService;
import com.overnet.project_sanatorio.service.ILicenciaOridinariaService;
import com.overnet.project_sanatorio.service.ILicenciaTomadaService;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
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
    @Autowired
    private ILicenciaOridinariaService licordser;
    @Autowired
    private IInasistenciaService inasiser;

    @GetMapping("/empleado/licencia/alta/{idEmpleado}")
    public String mostrarFormAltaDesdeEmpleado(Model modelo, @PathVariable int idEmpleado, 
            @RequestParam(required = false)Integer idSuperpuesta,
            @RequestParam(required = false)Integer idInasistenciaSup) {
        LocalDate fechaActal = LocalDate.now();
        int anio = fechaActal.getYear();
        List<TipoLicenciaDTO> dtos = lictomser.armarTipoLicenciaDTO(idEmpleado, anio);
        LicenciaTomada licencia = new LicenciaTomada();
        Empleado emp = empleadoser.findEmpleado(idEmpleado);
        modelo.addAttribute("anio", anio);
        modelo.addAttribute("empleado", emp);
        modelo.addAttribute("licencia", licencia);
        modelo.addAttribute("dtos", dtos);
        
        if (idSuperpuesta != null) {
            // Si hay un ID de licencia superpuesta, busca la licencia y agrégala al modelo
            LicenciaTomada superpuesta = lictomser.findLicenciaTomadaById(idSuperpuesta);
            modelo.addAttribute("superpuesta", superpuesta);
        }
        
        if (idInasistenciaSup != null) {
            Inasistencia inasistenciasup = inasiser.findInasistenciaById(idInasistenciaSup);
            modelo.addAttribute("inasistenciaSup", inasistenciasup);
        }

        return "alta_licencias_emp";
    }

    @PostMapping("/empleado/licencia/alta/")
    public String altaLicenciaDesdeEmpleado(@ModelAttribute("licencia") LicenciaTomada licencia, @RequestParam("idEmpleado") int idEmpleado, @RequestParam("idTipo") int idTipo) {
        licencia.setEmpleado(empleadoser.findEmpleado(idEmpleado));
        licencia.setTipoLicencia(lictomser.findById(idTipo));
        if (lictomser.findLicenciaTomadaSupepuesta(idEmpleado, licencia) != null) {
            LicenciaTomada superpuesta = lictomser.findLicenciaTomadaSupepuesta(idEmpleado, licencia);
            return "redirect:/empleado/licencia/alta/" + idEmpleado + "?idSuperpuesta=" + superpuesta.getIdLicenciaTomada();
        } else if (inasiser.findSuperpuestaAltaLicencia(idEmpleado, licencia.getFechaDesde(), licencia.getFechaHasta()) != null) {
            Inasistencia inasistencia = inasiser.findSuperpuestaAltaLicencia(idEmpleado, licencia.getFechaDesde(), licencia.getFechaHasta());
            return "redirect:/empleado/licencia/alta/" + idEmpleado + "?idInasistenciaSup=" + inasistencia.getId();
        } else {
            lictomser.saveLicenciaTomada(licencia);
            return "redirect:/empleado/licencia/ver/" + idEmpleado;
        }
    }

    @GetMapping("/empleado/licencia/ordinaria/alta/{idEmpleado}")
    public String mostrarFormAltaOrdinariaDesdeEmpleado(Model modelo, @PathVariable int idEmpleado,
            @RequestParam(required = false)Integer idSuperpuesta,
            @RequestParam(required = false)Integer idInasistenciaSup) {
        LocalDate fechaActual = LocalDate.now();
        int anioActual = fechaActual.getYear();
        int anioPeriodo = calcularPeriodo(anioActual);
        System.out.println("AÑO PERIODO: "+anioPeriodo);
        List<LicenciaOrdinaria> licords = lictomser.obtenerLicenciasOrdinarias(idEmpleado, anioPeriodo);
        licords.sort(Comparator.comparingInt(LicenciaOrdinaria::getAnio));
        int diasTotales = 0;
        for (LicenciaOrdinaria l : licords) {
            diasTotales += l.getDiasRestanes();
        }
        LicenciaTomada licencia = new LicenciaTomada();
        licencia.setCertificado(false);
        Empleado emp = empleadoser.findEmpleado(idEmpleado);
        modelo.addAttribute("licencia", licencia);
        modelo.addAttribute("licords", licords);
        modelo.addAttribute("anio", anioPeriodo);
        modelo.addAttribute("empleado", emp);
        modelo.addAttribute("dias", diasTotales);
        if (idSuperpuesta != null) {
            // Si hay un ID de licencia superpuesta, busca la licencia y agrégala al modelo
            LicenciaTomada superpuesta = lictomser.findLicenciaTomadaById(idSuperpuesta);
            modelo.addAttribute("superpuesta", superpuesta);
        }
        
        if (idInasistenciaSup != null) {
            Inasistencia inasistenciasup = inasiser.findInasistenciaById(idInasistenciaSup);
            modelo.addAttribute("inasistenciaSup", inasistenciasup);
        }
        return "alta_licenciasordinarias_emp";
    }

    @PostMapping("/empleado/licencia/ordinaria/alta/{idEmpleado}")
    public String altaLicenciaOrdinariaDesdeEmpleado(@PathVariable int idEmpleado, @ModelAttribute("licencia") LicenciaTomada licencia, @RequestParam("anio") int anio) {
        if(lictomser.findLicenciaTomadaSupepuesta(idEmpleado, licencia)==null && inasiser.findSuperpuestaAltaLicencia(idEmpleado, licencia.getFechaDesde(), licencia.getFechaHasta()) == null){    
            licencia.setEmpleado(empleadoser.findEmpleado(idEmpleado));
            licencia.setTipoLicencia(lictomser.findTipoLicenciaByNombre("Ordinaria"));
            lictomser.saveLicenciaTomada(licencia);
            int dias = (int) ChronoUnit.DAYS.between(licencia.getFechaDesde(), licencia.getFechaHasta());
            List<LicenciaOrdinaria> licords = lictomser.obtenerLicenciasOrdinarias(idEmpleado, anio);
            licords.sort(Comparator.comparingInt(LicenciaOrdinaria::getAnio));
            for (LicenciaOrdinaria licord : licords) {
                int diasRestantes = licord.getDiasRestanes();
                int diasConsumidos = Math.min(diasRestantes, dias);

                licord.setDiasRestanes(diasRestantes - diasConsumidos);
                dias -= diasConsumidos;

                if (dias == 0) {
                    break;
                }
            }
            licords.forEach(l -> {
                licordser.editarLicOrd(l);
            });
            return "redirect:/empleado/licencia/ver/" + idEmpleado;
        }else if(lictomser.findLicenciaTomadaSupepuesta(idEmpleado, licencia)!=null){
            LicenciaTomada superpuesta = lictomser.findLicenciaTomadaSupepuesta(idEmpleado, licencia);
            return "redirect:/empleado/licencia/ordinaria/alta/"+idEmpleado+"?idSuperpuesta="+superpuesta.getIdLicenciaTomada();
        }else if(inasiser.findSuperpuestaAltaLicencia(idEmpleado, licencia.getFechaDesde(), licencia.getFechaHasta())!=null){
            //se encontro una inasistencia que se superpone
            Inasistencia inasistencia = inasiser.findSuperpuestaAltaLicencia(idEmpleado, licencia.getFechaDesde(), licencia.getFechaHasta());
            return "redirect:/empleado/licencia/ordinaria/alta/"+idEmpleado+"?idInasistenciaSup="+inasistencia.getId();
        }
         return "redirect:/empleado/licencia/ver/" + idEmpleado;
    }

    @GetMapping("/empleado/licencia/alta/cancelar/{id}")
    public String volverAListaLicencias(@PathVariable int id) {
        return "redirect:/empleado/licencia/ver/" + id;
    }

    @GetMapping("/licencia/editar/{id}")
    public String mostrarFormEditar(@PathVariable int id, Model modelo,
            @RequestParam(required = false)Integer idSuperpuesta,
            @RequestParam(required = false)Integer idInasistenciaSup) {
        LicenciaTomada lic = lictomser.findLicenciaTomadaById(id);
        LocalDate fechaActal = LocalDate.now();
        int anio = fechaActal.getYear();
        Empleado emp = empleadoser.findEmpleado(lic.getEmpleado().getId());
        if (lic.getTipoLicencia().getNombre().equals("Ordinaria")) {
            int anioPeriodo = calcularPeriodo(lic.getFechaHasta().getYear());
            List<LicenciaOrdinaria> licords = lictomser.obtenerLicenciasOrdinariasParaEditar(lic.getEmpleado().getId(), anioPeriodo);
            licords.sort(Comparator.comparingInt(LicenciaOrdinaria::getAnio));
            int diasTotales = 0;
            for (LicenciaOrdinaria l : licords) {
                diasTotales += l.getDiasRestanes();
            }
            int diasConsumidos = (int) ChronoUnit.DAYS.between(lic.getFechaDesde(), lic.getFechaHasta());
            modelo.addAttribute("diasconsumidos", diasConsumidos);
            modelo.addAttribute("anio", anioPeriodo);
            modelo.addAttribute("dias", diasTotales);
            modelo.addAttribute("licencia", lic);
            modelo.addAttribute("empleado", emp);
            modelo.addAttribute("licords", licords);
            if (idSuperpuesta != null) {
            // Si hay un ID de licencia superpuesta, busca la licencia y agrégala al modelo
            LicenciaTomada superpuesta = lictomser.findLicenciaTomadaById(idSuperpuesta);
            System.out.println("SUPERPUESTA ID: "+superpuesta.getIdLicenciaTomada());
            System.out.println("SUPERPUESTA FECHA DESDE: "+superpuesta.getFechaDesde());
            System.out.println("SUPERPUESTA FECHA HASTA: "+superpuesta.getFechaHasta());
            modelo.addAttribute("superpuesta", superpuesta);
            }
            
            if(idInasistenciaSup != null){
                Inasistencia inasistencia = inasiser.findInasistenciaById(idInasistenciaSup);
                modelo.addAttribute("inasistenciaSup", inasistencia);
            }
            return "editar_licenciasordinarias_emp";
        } else {
            List<TipoLicenciaDTO> dtos = lictomser.armarTipoLicenciaDTO(lic.getEmpleado().getId(), lic.getFechaHasta().getYear());
            int diasConsumidos = (int) ChronoUnit.DAYS.between(lic.getFechaDesde(), lic.getFechaHasta());
            modelo.addAttribute("anio", lic.getFechaHasta().getYear());
            modelo.addAttribute("licencia", lic);
            modelo.addAttribute("empleado", emp);
            modelo.addAttribute("dtos", dtos);
            for(TipoLicenciaDTO dto : dtos){
                if(dto.getIdTipo()==lic.getTipoLicencia().getId()){
                    modelo.addAttribute("diasrestantes", dto.getDiasRestantes());
                }
            }
            modelo.addAttribute("diasconsumidos", diasConsumidos);
            System.out.println("PARAMETRO idSuperpuesta: "+idSuperpuesta);
            if (idSuperpuesta != null) {
            // Si hay un ID de licencia superpuesta, busca la licencia y agrégala al modelo
            LicenciaTomada superpuesta = lictomser.findLicenciaTomadaById(idSuperpuesta);
            System.out.println("SUPERPUESTA ID: "+superpuesta.getIdLicenciaTomada());
            System.out.println("SUPERPUESTA FECHA DESDE: "+superpuesta.getFechaDesde());
            System.out.println("SUPERPUESTA FECHA HASTA: "+superpuesta.getFechaHasta());
            modelo.addAttribute("superpuesta", superpuesta);
            }
            
            if(idInasistenciaSup != null){
                Inasistencia inasistencia = inasiser.findInasistenciaById(idInasistenciaSup);
                modelo.addAttribute("inasistenciaSup", inasistencia);
            }
            return "editar_licencias_emp";
        }
        

    }

    @PostMapping("/licencia/editar/{id}")
    public String editarLicencia(@PathVariable int id, @ModelAttribute("licencia") LicenciaTomada lic, Model modelo) {
        LicenciaTomada l = lictomser.findLicenciaTomadaById(id);
        lic.setIdLicenciaTomada(l.getIdLicenciaTomada());
        if(lictomser.findLicenciaTomadaSupepuestaExcluyendose(l.getEmpleado().getId(), lic)==null && inasiser.findSuperpuestaAltaLicencia(l.getEmpleado().getId(), lic.getFechaDesde(), lic.getFechaHasta())==null){
            if (l.getTipoLicencia().getNombre().equals("Ordinaria")) {
                int diasConsumidosOriginal = (int) ChronoUnit.DAYS.between(l.getFechaDesde(), l.getFechaHasta());
                int diasConsumidosNuevo = (int) ChronoUnit.DAYS.between(lic.getFechaDesde(), lic.getFechaHasta());
                int anioPeriodo = calcularPeriodo(LocalDate.now().getYear());
                List<LicenciaOrdinaria> licords = lictomser.obtenerLicenciasOrdinariasParaEditar(l.getEmpleado().getId(), anioPeriodo);
                if ((diasConsumidosOriginal - diasConsumidosNuevo) > 0) {
                    reasignarDiasLicenciasResta(licords, diasConsumidosOriginal - diasConsumidosNuevo);
                } else if ((diasConsumidosOriginal - diasConsumidosNuevo) < 0) {
                    reasignarDiasLicenciasSuma(licords, Math.abs(diasConsumidosOriginal - diasConsumidosNuevo));
                }
            }
            l.setCertificado(lic.isCertificado());
            l.setDescripcion(lic.getDescripcion());
            l.setFechaDesde(lic.getFechaDesde());
            l.setFechaHasta(lic.getFechaHasta());
            l.setTerminada(lic.isTerminada());
            lictomser.updateLicenciaTomada(l);
            return "redirect:/empleado/licencia/ver/" + l.getEmpleado().getId();
        }else if(lictomser.findLicenciaTomadaSupepuestaExcluyendose(l.getEmpleado().getId(), lic)!=null){
            LicenciaTomada superpuesta = lictomser.findLicenciaTomadaSupepuestaExcluyendose(l.getEmpleado().getId(), lic);
            return "redirect:/licencia/editar/"+l.getIdLicenciaTomada()+"?idSuperpuesta="+superpuesta.getIdLicenciaTomada();
        }else if(inasiser.findSuperpuestaAltaLicencia(l.getEmpleado().getId(), lic.getFechaDesde(), lic.getFechaHasta())!=null){
            Inasistencia inasistencia = inasiser.findSuperpuestaAltaLicencia(l.getEmpleado().getId(), lic.getFechaDesde(), lic.getFechaHasta());
            return "redirect:/licencia/editar/"+l.getIdLicenciaTomada()+"?idInasistenciaSup="+inasistencia.getId();
        }
        return "redirect:/empleado/licencia/ver/" + l.getEmpleado().getId();
    }

    @GetMapping("empleado/licencia/ver/detalle/{id}")
    public String detalleLicenciaDesdeEmp(@PathVariable int id, Model modelo) {
        LicenciaTomada l = lictomser.findLicenciaTomadaById(id);
        modelo.addAttribute("licencia", l);
        return "ver_licencia_emp";
    }

    @GetMapping("/licencia/detalle/{id}")
    public String detalleLicencia(@PathVariable int id, Model modelo) {
        LicenciaTomada l = lictomser.findLicenciaTomadaById(id);
        modelo.addAttribute("licencia", l);
        return "ver_licencia";
    }

    @GetMapping("/licencia/lista")
    public String traerLicencias(Model modelo, @Param("palabra") String palabra, @Param("terminada") boolean terminada) {

        if (palabra == null) {
            palabra = ""; //
        }
        System.out.println(!terminada);
        List<LicenciaTomada> lic = lictomser.getLicenciasTomadas(palabra, terminada);
        modelo.addAttribute("licencias", lic);
        modelo.addAttribute("palabra", palabra);
        modelo.addAttribute("terminada", terminada);

        return "lista_licenciastomadas";
    }

    public String calcularDiasRestantes(List<TipoLicenciaDTO> dtos, int idDto) {
        TipoLicenciaDTO tipolic = null;
        for (TipoLicenciaDTO t : dtos) {
            if (t.getIdTipo() == idDto) {
                tipolic = t;
            }
        }

        if (tipolic != null && tipolic.getDiasTotales() != null) {
            return String.valueOf(tipolic.getDiasTotales() - tipolic.getDiasTomados());
        } else {
            return "";
        }
    }

    @PostMapping("/licencia/baja/")
    public String eliminarLicencia(@RequestParam int id, HttpServletRequest request) {
        if(lictomser.findLicenciaTomadaById(id).getTipoLicencia().getNombre().equals("Ordinaria")){
            LicenciaTomada lictom = lictomser.findLicenciaTomadaById(id);
            int anioPeriodo = calcularPeriodo(lictom.getFechaDesde());
            List<LicenciaOrdinaria>licords = lictomser.obtenerLicenciasOrdinariasParaEditar(lictom.getEmpleado().getId(),anioPeriodo);
            int diasConsumidosOriginal = (int) ChronoUnit.DAYS.between(lictom.getFechaDesde(), lictom.getFechaHasta());
            reasignarDiasLicenciasResta(licords, diasConsumidosOriginal);
        }
        lictomser.deleteLicenciaTomada(id);
        String paginaAnterior = request.getHeader("Referer"); // Obtiene la URL de la página anterior desde la cabecera Referer
        if (paginaAnterior != null && !paginaAnterior.isEmpty()) {
            return "redirect:" + paginaAnterior;
        } else {
            return "redirect:/empleado/ver";
        }
    }

    private int calcularPeriodo(int anio) {
        LocalDate fechaActal = LocalDate.now();
        LocalDate fechaLimite = LocalDate.of(anio, 10, 1);
        int anioPeriodo;
        if (fechaActal.isBefore(fechaLimite)) {
            anioPeriodo = anio - 1;
        } else {
            anioPeriodo = anio;
        }
        return anioPeriodo;
    }
    
    private static int calcularPeriodo(LocalDate fecha) {
        LocalDate fechaLimite = LocalDate.of(fecha.getYear(), 10, 1);
        int anioPeriodo;
        if (fecha.isBefore(fechaLimite)) {
            anioPeriodo = fecha.getYear() - 1;
        } else {
            anioPeriodo = fecha.getYear();
        }
        return anioPeriodo;
    }

    private void reasignarDiasLicenciasResta(List<LicenciaOrdinaria> licords, int diasReasignados) {
        licords.sort(Comparator.comparingInt(LicenciaOrdinaria::getAnio).reversed());
        System.out.println("---------------|||-------------");
        System.out.println("---------------|||-------------");
        System.out.println("------------|||||||||----------");
        System.out.println("ENTRO A reasignarDiasLicenciasRESTA");
        System.out.println("LICORDS IS EMPTY?" + licords.isEmpty());
        System.out.println("DIAS REASIGNADOS: " + diasReasignados);

        int index = 0;

        while (diasReasignados != 0 && index < licords.size()) {
            LicenciaOrdinaria licencia = licords.get(index);

            System.out.println("AÑO " + licencia.getAnio());
            int diasRestantes = licencia.getDiasRestanes();
            System.out.println("DIAS RESTANTES: " + diasRestantes);

            // Reasigna los días a la licencia actual
            int diasAsignados = Math.min(licencia.getCantidadDeDias() - diasRestantes, Math.abs(diasReasignados));
            licencia.setDiasRestanes(diasRestantes + diasAsignados);
            diasReasignados -= diasAsignados;

            System.out.println("DIAS ASIGNADOS: " + diasAsignados);
            System.out.println("LICENCIA.setDiasRESTANTES= " + (diasRestantes + diasAsignados));

            index++;
        }

        System.out.println("SALIO DE reasignarDiasLicenciasRESTA");
        System.out.println("------------|||||||||----------");
        System.out.println("---------------|||-------------");
        System.out.println("---------------|||-------------");

        for (LicenciaOrdinaria licencia : licords) {
            licordser.editarLicOrd(licencia);
        }
    }

    private void reasignarDiasLicenciasSuma(List<LicenciaOrdinaria> licords, int diasReasignados) {
        licords.sort(Comparator.comparingInt(LicenciaOrdinaria::getAnio));
        System.out.println("---------------|||-------------");
        System.out.println("---------------|||-------------");
        System.out.println("------------|||||||||----------");
        System.out.println("ENTRO A reasignarDiasLicenciasSuma");
        System.out.println("LICORDS IS EMPTY?" + licords.isEmpty());
        System.out.println("DIAS REASIGNADOS: " + diasReasignados);
        for (LicenciaOrdinaria licord : licords) {
            int diasRestantes = licord.getDiasRestanes();
            int diasConsumidos = Math.min(diasRestantes, diasReasignados);

            licord.setDiasRestanes(diasRestantes - diasConsumidos);
            diasReasignados -= diasConsumidos;

            if (diasReasignados == 0) {
                break;
            }
        }
        licords.forEach(l -> {
            licordser.editarLicOrd(l);
        });

    }

}
