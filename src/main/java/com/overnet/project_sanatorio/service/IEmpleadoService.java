package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Empleado;
import java.math.BigInteger;
import java.util.List;


public interface IEmpleadoService {
    public List<Empleado> getEmpleados(String palabra, boolean deBaja);
    public void saveEmpleado(Empleado empleado);
    public void bajaEmpleado(int id);
    public void altaEmpleado(int id);
    public Empleado findEmpleado(int id);
    public void updateEmpleado(Empleado empleado);
    public List<Empleado> getEmpleadosByNombreApellidoNroLeg(String filtro);
    public int nextNroLegajo();
    public boolean existeNroLegajo(BigInteger nroLegajo);
    public boolean existeNroLegajoExceptoOriginal(BigInteger nroLegajo, BigInteger nroLegajoOriginal);
    public int countEmpleados();
    public int countEmpleadosNoActivos();
    public int countEmpleadosActivos();
    public int countEmpleadosPorSector(String sector);
}
