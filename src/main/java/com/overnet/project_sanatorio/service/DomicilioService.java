package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Domicilio;
import com.overnet.project_sanatorio.repository.IDomicilioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomicilioService implements IDomicilioService {
    @Autowired
    IDomicilioRepository domiciliorep;

    @Override
    public List<Domicilio> getDomcilios() {
        return domiciliorep.findAll();
    }

    @Override
    public Domicilio findDocimicilio(int id) {
         return domiciliorep.findById(id).orElse(null);
               
    }

    @Override
    public void saveDomicilio(Domicilio domicilio) {
        domiciliorep.save(domicilio);
    }

    @Override
    public void updateDomicilio(Domicilio domicilio) {
        domiciliorep.save(domicilio);
    }

    @Override
    public void bajaDomicilio(int id) {
       domiciliorep.darDeBaja(id);
    }
    
}
