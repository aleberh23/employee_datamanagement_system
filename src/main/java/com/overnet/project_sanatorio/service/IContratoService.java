package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Contrato;
import java.time.LocalDate;
import java.util.List;

public interface IContratoService {
    public List<Contrato> getContratos();
    public Contrato findContrato(int idContrato);
    public void updateContrato(Contrato contrato);
    public void bajaContrato(int idContrato);
    public void saveContrato(Contrato contrato);
    public List<Contrato> findBySearch(String palabra, boolean deBaja);
    public void verificarContratos();
    public void verificarContratosInicio();
    public Contrato findContratoSupepuesto(int idEmpleado, Contrato nuevoContrato);
    public Contrato findContratoSupepuestoExcluyendose(int idEmpleado, Contrato contrtatoEditado);
    public LocalDate obtenerUltimaFechaFin(int idEmpleado);
    public LocalDate obtenerUltimaFechaFinExceptuando(int idEmpleado, int idContrato);
    public int countContratosActivos();
    public Contrato findContratoProximoAFinalizarPorEmpleado(int idEmpleado);
}
