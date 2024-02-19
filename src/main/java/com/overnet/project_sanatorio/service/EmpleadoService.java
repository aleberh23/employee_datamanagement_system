package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Contrato;
import com.overnet.project_sanatorio.model.Empleado;
import com.overnet.project_sanatorio.repository.IEmpleadoRepository;
import jakarta.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService implements IEmpleadoService {
    @Autowired
    private IEmpleadoRepository empleadorep;


    @Override
    public void saveEmpleado(Empleado empleado) {
        empleadorep.save(empleado);
    }

    @Override
    public void bajaEmpleado(int id) {
        empleadorep.darDeBaja(id);
    }
    
     @Override
    public void altaEmpleado(int id) {
        empleadorep.darDeAlta(id);
    }

    @Override
    public Empleado findEmpleado(int id) {
       return empleadorep.findById(id).orElse(null);
    
    }

    @Override
    public void updateEmpleado(Empleado empleado) {
        Empleado emp = findEmpleado(empleado.getId());
        emp.setNombre(empleado.getNombre());
        emp.setApellido(empleado.getApellido());
        emp.setCorreoElectronico(empleado.getCorreoElectronico());
        emp.setNroCelular(empleado.getNroCelular());
        emp.setNroDocumento(empleado.getNroDocumento());
        emp.setTipoDocumento(empleado.getTipoDocumento());
        emp.setNroLegajo(empleado.getNroLegajo());
        emp.setCuil(empleado.getCuil());
        emp.setFechaIngreso(empleado.getFechaIngreso());
        emp.setFechaNacimiento(empleado.getFechaNacimiento());
        emp.setSector(empleado.getSector());
        emp.setSexo(empleado.getSexo());
        emp.setEstudiante(empleado.isEstudiante());
        
        empleadorep.save(emp);
        
    }

    @Override
    public List<Empleado> getEmpleados(String palabra, boolean deBaja) {
        if(!palabra.isEmpty()){
            System.out.println("Palabra no vacia");
            return empleadorep.findAll(palabra, deBaja);
           
        }
        System.out.println("Palabra vacia");
        return empleadorep.findAll(deBaja);
    }

    @Override
    public List<Empleado> getEmpleadosByNombreApellidoNroLeg(String filtro) {
        return empleadorep.findByNombreApellidoNroLegajo(filtro);
    }

    @Override
    public int nextNroLegajo() {
        return empleadorep.maxNroLegajo()+1;
    }

    @Override
    public boolean existeNroLegajo(BigInteger nroLegajo) {
        return empleadorep.existeNroLegajo(nroLegajo);
    }

    @Override
    public boolean existeNroLegajoExceptoOriginal(BigInteger nroLegajo, BigInteger nroLegajoOriginal) {
        return empleadorep.existeNroLegajoExceptoOriginal(nroLegajo, nroLegajoOriginal);
    }
    
    //METODOS PARA SETEAR SI UN PERSONAL ES CONTRATADO O NO DEPENDIENDO DE SUS CONTRATOS
    /*@PostConstruct
    public void procesarContratosInicio(){
        procesarContratos();
    }
    
    @Scheduled(cron = "0 0 0 * * ?") // Ejecutar todos los días a medianoche
    public void procesarContratos(){
        List<Empleado> emps = empleadorep.findAll();
        for (Empleado e : emps){
            boolean tieneContratos;
            if(e.getContratos().isEmpty()){
                e.setContratado(false);
            }else{
                boolean todosDeBaja = true;
                for(Contrato con : e.getContratos()){
                    if(!con.isBaja()){
                        todosDeBaja=false;
                    }
                }
                if(todosDeBaja){
                    e.setContratado(false);
                }else{
                    e.setContratado(true);
                }   
            }
  
        }
    }*/

    @Override
    public int countEmpleados() {
        return empleadorep.countEmpleados();
    }

    @Override
    public int countEmpleadosNoActivos() {
        return empleadorep.countEmpleadosNoActivos();
    }

    @Override
    public int countEmpleadosActivos() {
        return empleadorep.countEmpleadosActivos();
    }

    @Override
    public int countEmpleadosPorSector(String sector) {
        return empleadorep.countEmpleadosPorSector(sector);
    }
    
    
}
