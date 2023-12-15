package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Empleado;
import java.util.List;


public interface IEmpleadoService {
    public List<Empleado> getEmpleados(String palabra, boolean deBaja);
    public void saveEmpleado(Empleado empleado);
    public void bajaEmpleado(int id);
    public void altaEmpleado(int id);
    public Empleado findEmpleado(int id);
    public void updateEmpleado(Empleado empleado);
    public List<Empleado> getEmpleadosByNombreApellidoNroLeg(String filtro);
}
