package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.SectorDetalleParteDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISectorDetalleParteDiarioRepository extends JpaRepository<SectorDetalleParteDiario, Integer>{
     public SectorDetalleParteDiario findByNombre(String nombre);
}
