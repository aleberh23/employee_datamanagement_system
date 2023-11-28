package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Contrato;
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
}
