package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Sexo;
import com.overnet.project_sanatorio.repository.ISexoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SexoService implements ISexoService{
    @Autowired
    private ISexoRepository sexorep;

    @Override
    public Sexo findById(int idSexo) {
        return sexorep.findById(idSexo).orElse(null);
    }

    @Override
    public List<Sexo> findAl() {
        return sexorep.findAll();
    }
    
}
