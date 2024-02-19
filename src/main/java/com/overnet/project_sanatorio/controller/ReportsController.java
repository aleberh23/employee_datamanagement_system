package com.overnet.project_sanatorio.controller;

import com.overnet.project_sanatorio.dto.CargaDeFamiliaReporteDTO;
import com.overnet.project_sanatorio.dto.ContratoReporteDTO;
import com.overnet.project_sanatorio.dto.DomicilioReporteDTO;
import com.overnet.project_sanatorio.dto.EmpleadoReporteDTO;
import com.overnet.project_sanatorio.dto.InasistenciaReporteDTO;
import com.overnet.project_sanatorio.dto.LicenciaReporteDTO;
import com.overnet.project_sanatorio.model.CargaDeFamilia;
import com.overnet.project_sanatorio.model.Contrato;
import com.overnet.project_sanatorio.model.DetalleParteDiario;
import com.overnet.project_sanatorio.model.Domicilio;
import com.overnet.project_sanatorio.model.Empleado;
import com.overnet.project_sanatorio.model.Inasistencia;
import com.overnet.project_sanatorio.model.LicenciaTomada;
import com.overnet.project_sanatorio.model.ParteDiario;
import com.overnet.project_sanatorio.service.IEmpleadoService;
import com.overnet.project_sanatorio.service.IParteDiarioService;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReportsController {
    @Autowired
    private IEmpleadoService empser;
    @Autowired
    private IParteDiarioService pdser;
    
    
    @GetMapping("/empleado/reporte/{id}")
    public ResponseEntity<byte[]> generateReport(@PathVariable int id) throws IOException, JRException {
        //obtener empleado
        Empleado emp = empser.findEmpleado(id);
        
        // Crear un mapa de parámetros para el informe
        Map<String, Object> params = new HashMap<>();

        // Cargar el archivo .jasper desde el classpath
        ClassPathResource resource = new ClassPathResource("reports/reporte_empleado.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(resource.getInputStream());

        EmpleadoReporteDTO empleado = obtenerEmpleadoDTO(emp);
        List<DomicilioReporteDTO> domicilios = obtenerDomiciliosDTO(emp); 
        List<ContratoReporteDTO> contratos = obtenerContratosDTO(emp);
        List<CargaDeFamiliaReporteDTO> cargasDeFamilia = obtenerCargasDeFamDTO(emp);
        List<LicenciaReporteDTO> licencias = obtenerLicenciasDTO(emp);
        
        //convertir lista en array de objetos
        DomicilioReporteDTO[] arrayDomiciliosDTO = domicilios.toArray(new DomicilioReporteDTO[domicilios.size()]);
        ContratoReporteDTO[] arrayContratosDTO = contratos.toArray(new ContratoReporteDTO[contratos.size()]);
        CargaDeFamiliaReporteDTO[] arrayCargasFamDTO = cargasDeFamilia.toArray(new CargaDeFamiliaReporteDTO[cargasDeFamilia.size()]);
        LicenciaReporteDTO[] arrayLicenciasDTO = licencias.toArray(new LicenciaReporteDTO[licencias.size()]);
        
        //convertit en data source
        JRBeanArrayDataSource domiciliosDataSource = new JRBeanArrayDataSource(arrayDomiciliosDTO);
        JRBeanArrayDataSource cFamDataSource = new JRBeanArrayDataSource(arrayCargasFamDTO);
        JRBeanArrayDataSource contratosDataSource = new JRBeanArrayDataSource(arrayContratosDTO);
        JRBeanArrayDataSource licenciasDataSource = new JRBeanArrayDataSource(arrayLicenciasDTO);
        
        //atributos booleanos
        boolean tieneDomicilios = (domicilios.isEmpty())? false: true;
        boolean tieneCargasDeFam = (cargasDeFamilia.isEmpty())? false: true;
        boolean tieneContratos = (contratos.isEmpty())? false: true;
        boolean tieneLicencias = (licencias.isEmpty())? false: true;
        
        //poner los parametros
        params.put("domiciliosDataSource", domiciliosDataSource);
        params.put("cFamDataSource", cFamDataSource);
        params.put("contratosDataSource", contratosDataSource);
        params.put("licenciasDataSource", licenciasDataSource);
        
        params.put("tieneDomicilios", tieneDomicilios);
        params.put("tieneCargasDeFam", tieneCargasDeFam);
        params.put("tieneContratos",tieneContratos);
        params.put("tieneLicencias", tieneLicencias);
        
        //generar reporte
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JRBeanCollectionDataSource(Collections.singletonList(empleado)));

        // Convertir el informe a un array de bytes en formato PDF
        byte[] reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        // Configurar el encabezado de la respuesta HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "reporte_"+emp.getNombre().toLowerCase().trim()+"_"+emp.getApellido().toLowerCase().trim()+".pdf");

        // Retornar la respuesta con el informe en formato PDF
        return ResponseEntity.ok().headers(headers).body(reportBytes);
    }
    
    @GetMapping("/partediario/generarpdf")
    public ResponseEntity<byte[]> generatePdiarioReport(@RequestParam(name="fecha")LocalDate fecha) throws IOException, JRException {
        DateTimeFormatter formateadorNombreArchivo = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ParteDiario pd = pdser.findByDate(fecha);
        
        // Crear un mapa de parámetros para el informe
        Map<String, Object> params = new HashMap<>();

        // Cargar el archivo .jasper desde el classpath
        ClassPathResource resource = new ClassPathResource("reports/reporte_pdiario.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(resource.getInputStream());
        
        //obtener listas
        List<EmpleadoReporteDTO> jubilaciones = obtenerJubilacionesPD(pd);
        List<LicenciaReporteDTO> licencias = obtenerLicenciasPD(pd);
        List<ContratoReporteDTO> contratos = obtenerPersonalContratadoPD(pd);
        List<InasistenciaReporteDTO> inasistencias = obtenerInasistenciasPD(pd);
        
        //convertir lista en array de objetos
        EmpleadoReporteDTO[] arrayJubilacionesDTO = jubilaciones.toArray(new EmpleadoReporteDTO[jubilaciones.size()]);
        ContratoReporteDTO[] arrayContratosDTO = contratos.toArray(new ContratoReporteDTO[contratos.size()]);
        LicenciaReporteDTO[] arrayLicenciasDTO = licencias.toArray(new LicenciaReporteDTO[licencias.size()]);
        InasistenciaReporteDTO[] arrayInasistenciasDTO = inasistencias.toArray(new InasistenciaReporteDTO[inasistencias.size()]);
        
        //convertit en data source
        JRBeanArrayDataSource jubilacionesDataSource = new JRBeanArrayDataSource(arrayJubilacionesDTO);
        JRBeanArrayDataSource contratosDataSource = new JRBeanArrayDataSource(arrayContratosDTO);
        JRBeanArrayDataSource licenciasDataSource = new JRBeanArrayDataSource(arrayLicenciasDTO);   
        JRBeanArrayDataSource inasistenciasDataSource = new JRBeanArrayDataSource(arrayInasistenciasDTO);
        
        //atributos booleanos
        boolean tieneJubilaciones = (jubilaciones.isEmpty())? false: true;
        boolean tieneContratos = (contratos.isEmpty())? false: true;
        boolean tieneLicencias = (licencias.isEmpty())? false: true;
        boolean tieneInasistencias = (inasistencias.isEmpty())? false: true;
        
        //poner los parametros
        params.put("jubilacionesDataSource", jubilacionesDataSource);
        params.put("licenciasDataSource", licenciasDataSource);
        params.put("contratosDataSource", contratosDataSource);
        params.put("inasistenciasDataSource", inasistenciasDataSource);
        params.put("tieneJubilaciones", tieneJubilaciones);
        params.put("tieneContratos", tieneContratos);
        params.put("tieneLicencias", tieneLicencias);
        params.put("tieneInasistencias", tieneInasistencias);
        params.put("fecha", formateador.format(fecha));
        
        // Crear un JREmptyDataSource
        JREmptyDataSource emptyDataSource = new JREmptyDataSource();
        
        //generar reporte
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, emptyDataSource);

        // Convertir el informe a un array de bytes en formato PDF
        byte[] reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "reporte_parte_diario_"+formateadorNombreArchivo.format(fecha)+".pdf");

        // Retornar la respuesta con el informe en formato PDF
        return ResponseEntity.ok().headers(headers).body(reportBytes);
    }
    
    public EmpleadoReporteDTO obtenerEmpleadoDTO(Empleado emp){
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        EmpleadoReporteDTO empdto = new EmpleadoReporteDTO();
        empdto.setNombre(emp.getNombre());
        empdto.setApellido(emp.getApellido());
        empdto.setActivo((emp.isBaja())? "No": "Si");
        empdto.setContratado((emp.isContratado())? "Si":"No");
        empdto.setCorreoElectronico(emp.getCorreoElectronico());
        empdto.setEstudiante((emp.isEstudiante())? "Si":"No");
        empdto.setFechaBaja((emp.getFechaBaja()==null)? "-" : formateador.format(emp.getFechaBaja()));
        empdto.setFechaIngreso(formateador.format(emp.getFechaIngreso()));
        empdto.setFechaJubilacion((emp.getFechaJubilacion()==null)? "-":formateador.format(emp.getFechaJubilacion()));
        empdto.setFechaNacimiento(formateador.format(emp.getFechaNacimiento()));
        empdto.setNroCuil(emp.getCuil().toString());
        empdto.setNroDocumento(emp.getNroDocumento().toString());
        empdto.setNroLegajo(emp.getNroLegajo().toString());
        empdto.setNroTelefono(emp.getNroCelular().toString());
        empdto.setSector(emp.getSector().getNombre());
        empdto.setSexo(emp.getSexo().getNombre());
        empdto.setTipoDocumento(emp.getTipoDocumento());
        
        return empdto;        
    }
    
    public List<DomicilioReporteDTO> obtenerDomiciliosDTO (Empleado emp){
        List<DomicilioReporteDTO> domicilios = new ArrayList<>();
        for(Domicilio dom : emp.getDomicilios()){
            DomicilioReporteDTO domdto = new DomicilioReporteDTO();
            domdto.setCalle(dom.getCalle());
            domdto.setActivo((dom.isBaja())?"No":"Si");
            domdto.setAuditoria((dom.isAuditoriaMedica())?"Si":"No");
            domdto.setLocalidad(dom.getLocalidad());
            domdto.setNro(dom.getNumero().toString());
            domicilios.add(domdto);            
        }
        
        return domicilios;
    }
    
    public List<CargaDeFamiliaReporteDTO> obtenerCargasDeFamDTO (Empleado emp) {
        List<CargaDeFamiliaReporteDTO> cargasdefamilia = new ArrayList<>();
        for(CargaDeFamilia cf : emp.getCargasDeFamilia()){
            CargaDeFamiliaReporteDTO cfdto = new CargaDeFamiliaReporteDTO();
            cfdto.setActiva((cf.isBaja())?"No":"Si");
            cfdto.setNombre(cf.getNombre()+" "+cf.getApellido());
            cfdto.setNroDoc(cf.getNroDoc().toString());
            cfdto.setTipoDoc(cf.getTipoDoc());
            cfdto.setVinculo(cf.getVinculo());
            cargasdefamilia.add(cfdto);
        }
        return cargasdefamilia;
    }
    
    public List<ContratoReporteDTO> obtenerContratosDTO (Empleado emp){
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<ContratoReporteDTO> contratos = new ArrayList<>();
        for(Contrato con : emp.getContratos()){
            ContratoReporteDTO condto = new ContratoReporteDTO();
            condto.setEstado(con.getEstado());
            condto.setFechaFin(formateador.format(con.getFechaFin()));
            condto.setFechaInicio(formateador.format(con.getFechaInicio()));
            
            contratos.add(condto);
        }
        return contratos;
    }
    
    public List<LicenciaReporteDTO> obtenerLicenciasDTO (Empleado emp){
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<LicenciaReporteDTO> licencias = new ArrayList<>();
        for(LicenciaTomada lic : emp.getLicenciasTomadas()){
            LicenciaReporteDTO licdto = new LicenciaReporteDTO();
            licdto.setEstado((lic.isTerminada())? "Terminada":"Activa");
            licdto.setFechaDesde(formateador.format(lic.getFechaDesde()));
            licdto.setFechaHasta(formateador.format(lic.getFechaHasta()));
            licdto.setTipo(lic.getTipoLicencia().getNombre());
            
            licencias.add(licdto);
        }
        return licencias;
    }
    
    public List<EmpleadoReporteDTO> obtenerJubilacionesPD(ParteDiario pd){
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<EmpleadoReporteDTO> jubilacionesDTO = new ArrayList<>();
        DetalleParteDiario dpd = findByName("Jubilaciones", pd.getDetallespd());
        List<Empleado> jubilaciones = dpd.getEmpleados();
        for (Empleado emp : jubilaciones){
            EmpleadoReporteDTO empdto = new EmpleadoReporteDTO();
            empdto.setNroLegajo(emp.getNroLegajo().toString());
            empdto.setNombreyapellido(emp.getNombre()+" "+emp.getApellido());
            empdto.setSector(emp.getSector().getNombre());
            empdto.setFechaNacimiento(formateador.format(emp.getFechaNacimiento()));
            empdto.setFechaIngreso(formateador.format(emp.getFechaIngreso()));
            empdto.setFechaJubilacion(formateador.format(emp.getFechaJubilacion()));
            
            jubilacionesDTO.add(empdto);
        }
        return jubilacionesDTO;
    }
    
    public List<LicenciaReporteDTO> obtenerLicenciasPD (ParteDiario pd){
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<LicenciaReporteDTO> licenciasDTO = new ArrayList<>();
        DetalleParteDiario dpd = findByName("Licencias", pd.getDetallespd());
        List<LicenciaTomada> licencias = dpd.getLicTomadas();
        for(LicenciaTomada lic : licencias){
            LicenciaReporteDTO licdto = new LicenciaReporteDTO();
            licdto.setNroLegajo(lic.getEmpleado().getNroLegajo().toString());
            licdto.setEmpleado(lic.getEmpleado().getNombre()+" "+lic.getEmpleado().getApellido());
            licdto.setSector(lic.getEmpleado().getSector().getNombre());
            licdto.setFechaDesde(formateador.format(lic.getFechaDesde()));
            licdto.setFechaHasta(formateador.format(lic.getFechaHasta()));
            licdto.setTipo(lic.getTipoLicencia().getNombre());
            if(!lic.getTipoLicencia().getNombre().equals("Ordinaria")){
                licdto.setCertificado((lic.isCertificado())? "Si": "No");
            }else{
                licdto.setCertificado("-");
            }
            
            licenciasDTO.add(licdto);
        }
        return licenciasDTO;
    }
    
    public List<ContratoReporteDTO> obtenerPersonalContratadoPD (ParteDiario pd) {
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<ContratoReporteDTO> contratosDTO = new ArrayList<>();
        DetalleParteDiario dpd = findByName("Contratos", pd.getDetallespd());
        List<Contrato> contratos = dpd.getContratos();
        for(Contrato con : contratos){
            ContratoReporteDTO condto = new ContratoReporteDTO();
            condto.setNroLegajo(con.getEmpleado().getNroLegajo().toString());
            condto.setEmpleado(con.getEmpleado().getNombre()+" "+con.getEmpleado().getApellido());
            condto.setSector(con.getEmpleado().getSector().getNombre());
            condto.setFechaInicio(formateador.format(con.getFechaInicio()));
            condto.setFechaFin(formateador.format(con.getFechaFin()));
            condto.setDescripcion(con.getDescripcion());
            
            contratosDTO.add(condto);
        }
        return contratosDTO;
    }
    
    public List<InasistenciaReporteDTO> obtenerInasistenciasPD (ParteDiario pd) {
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<InasistenciaReporteDTO> inasistenciasDTO = new ArrayList<>();
        DetalleParteDiario dpd = findByName("Inasistencias", pd.getDetallespd());
        List<Inasistencia> inasistencias = dpd.getInasistencias();
        for (Inasistencia ina : inasistencias){
            InasistenciaReporteDTO inadto = new InasistenciaReporteDTO();
            inadto.setNroLegajo(ina.getEmpleado().getNroLegajo().toString());
            inadto.setEmpleado(ina.getEmpleado().getNombre()+" "+ina.getEmpleado().getApellido());
            inadto.setSector(ina.getEmpleado().getSector().getNombre());
            inadto.setFecha(formateador.format(ina.getFecha()));
            inadto.setMotivo(ina.getTipoInasistencia().getNombre());
            inadto.setDescripcion(ina.getDescripcion());
            inadto.setCertificado((ina.isCertificado())? "Si":"No");
            
            inasistenciasDTO.add(inadto);
        }
        return inasistenciasDTO;
    }
    
    public DetalleParteDiario findByName(String name, List<DetalleParteDiario>detalles){
        for(DetalleParteDiario dpd : detalles){
            if(dpd.getSector().getNombre().equals(name)){
                return dpd;
            }
        }
        return null;
    }
}

