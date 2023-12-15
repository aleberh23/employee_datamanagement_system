package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Contrato;
import com.overnet.project_sanatorio.model.Empleado;
import com.overnet.project_sanatorio.repository.IContratoRepository;
import com.overnet.project_sanatorio.repository.IEmpleadoRepository;
import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ContratoService implements IContratoService {
    @Autowired
    private IContratoRepository contratorep;
    @Autowired
    private IEmpleadoRepository empleadorep;
    
    @Override
    public List<Contrato> getContratos() {
        return contratorep.findAll();
    }

    @Override
    public Contrato findContrato(int idContrato) {
        return contratorep.findById(idContrato).orElse(null);
    }

    @Override
    public void updateContrato(Contrato contrato) {
        contratorep.save(contrato);
    }

    @Override
    public void bajaContrato(int idContrato) {
        Contrato c = contratorep.findById(idContrato).orElse(null);
        c.setEstado("Cancelado");
        contratorep.darDeBaja(idContrato);
        contratorep.save(c);
        if(contratorep.contadorContratosEmpleado(c.getEmpleado().getId())<1){
            Empleado e = empleadorep.findById(c.getEmpleado().getId()).orElse(null);
            e.setContratado(false);
            empleadorep.save(e);
        }
    }

    @Override
    public void saveContrato(Contrato contrato) {
        contrato.setEstado("Vigente");
        contratorep.save(contrato);
        if(contratorep.contadorContratosEmpleado(contrato.getEmpleado().getId())>0){
            Empleado e = contrato.getEmpleado();
            e.setContratado(true);
            empleadorep.save(e);
        }
    }
    
    @PostConstruct //ejecutar cuando arranca el sistema
    public void verificarContratosInicio(){
        verificarContratos();
    }
    @Scheduled(cron = "0 0 0 * * ?") // Ejecutar todos los días a medianoche
    public void verificarContratos() {
        List<Contrato> contratos = contratorep.findAllActivos();
        LocalDate fechaActual = LocalDate.now();
        for (Contrato contrato : contratos) {
            if (contrato.getFechaFin() != null && contrato.getFechaFin().isBefore(fechaActual)) {
                contrato.setBaja(true);
                contrato.setEstado("Finalizado");
                contratorep.save(contrato);
                  if(contratorep.contadorContratosEmpleado(contrato.getEmpleado().getId())<1){
                      Empleado e = contrato.getEmpleado();
                      System.out.println("service: nombre= "+e.getNombre()+" "+e.getApellido());
                      e.setContratado(false);
                      empleadorep.save(e);
                  }
                
            }
        }
    }

    @Override
    public List<Contrato> findBySearch(String palabra, boolean deBaja) {
        return contratorep.findAll(palabra, deBaja);
    }

    @Override
    public Contrato findContratoSupepuesto(int idEmpleado, Contrato nuevoContrato) {
        List<Contrato>superpuestos=contratorep.findSuperpuesto(idEmpleado, nuevoContrato.getFechaInicio(), nuevoContrato.getFechaFin());
        Contrato contratosuperpuesto = superpuestos.stream().findFirst().orElse(null);
        return contratosuperpuesto;
    }

    @Override
    public Contrato findContratoSupepuestoExcluyendose(int idEmpleado, Contrato contrtatoEditado) {
        List<Contrato>superpuestos=contratorep.findSuperpuestoEditar(idEmpleado, contrtatoEditado.getFechaInicio(), contrtatoEditado.getFechaFin(), contrtatoEditado.getIdContrato());
        Contrato contratosuperpuesto = superpuestos.stream().findFirst().orElse(null);
        return contratosuperpuesto;
    }
    
}
