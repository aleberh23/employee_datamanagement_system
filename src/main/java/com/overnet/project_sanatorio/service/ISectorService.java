package com.overnet.project_sanatorio.service;
import com.overnet.project_sanatorio.model.Sector;
import java.util.List;
public interface ISectorService {
    public List<Sector> getSectores();
    public Sector findSectorById(int id);
}
