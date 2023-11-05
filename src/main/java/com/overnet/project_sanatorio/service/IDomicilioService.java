package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Domicilio;
import java.util.List;

public interface IDomicilioService {
    public List<Domicilio> getDomcilios();
    public Domicilio findDocimicilio(int id);
    public void saveDomicilio(Domicilio domicilio);
    public void updateDomicilio(Domicilio domicilio);
    public void bajaEmpleado(int id);
}
