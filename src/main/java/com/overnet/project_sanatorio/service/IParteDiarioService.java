package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.model.DetalleParteDiario;
import com.overnet.project_sanatorio.model.ParteDiario;
import java.time.LocalDate;
import java.util.List;


public interface IParteDiarioService {
    public List<ParteDiario> findAll();
    public ParteDiario findByDate(LocalDate fecha);
    public void save(ParteDiario pdiario);
    public void generate(ParteDiario pdiario);
    public ParteDiario findById(int id);
    public DetalleParteDiario findByIdParteDiarioAndNombreSector(int idPd, String nombreSec);
    public void updateParteDiario(int idParteDIario);
    public List<String> getPartesDiarioForAusencia(LocalDate fecha);
}
