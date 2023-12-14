package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.dto.LicenciaOrdinariaDTO;
import com.overnet.project_sanatorio.model.LicenciaOrdinaria;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ILicenciaOridinariaService {
    public List<LicenciaOrdinaria>findAll();
    public void guardarLicOrd(LicenciaOrdinariaDTO licord);
    public void editarLicOrd(LicenciaOrdinaria licord);
    public HashMap<Integer,LicenciaOrdinariaDTO> crearDtos(Integer año);
    public void eliminarLicenciasOrdinarias(Integer anio);
    
}
