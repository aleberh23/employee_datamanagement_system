package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Empleado;
import com.overnet.project_sanatorio.repository.IEmpleadoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    
    
}
