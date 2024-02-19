package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.Sexo;
import java.util.List;


public interface ISexoService {
    public Sexo findById(int idSexo);
    public List<Sexo> findAl();
}
