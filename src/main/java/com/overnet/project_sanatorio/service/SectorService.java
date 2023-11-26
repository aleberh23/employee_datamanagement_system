package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Sector;
import com.overnet.project_sanatorio.repository.ISectorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectorService implements ISectorService {
    @Autowired
    private ISectorRepository sectorrep;
    @Override
    public List<Sector> getSectores() {
       return sectorrep.findAll();
    }

    @Override
    public Sector findSectorById(int id) {
       return sectorrep.findById(id).orElse(null);
    }
    
}
