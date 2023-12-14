package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.DetalleParteDiario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetalleParteDiarioRepository extends JpaRepository<DetalleParteDiario, Integer> {
    @Query("SELECT dpd FROM detalle_pdiario dpd JOIN dpd.sector sec " +  
       "WHERE dpd.parteDiario.id = ?1 " +  
       "AND sec.nombre = ?2")
    public DetalleParteDiario findByParteDiarioAndSector(int idParteDiario, String nombreSector);
    
   @Query("SELECT dpd FROM detalle_pdiario dpd WHERE dpd.parteDiario.id = ?1")
   public List<DetalleParteDiario> findByParteDiarioId(int idParteDIario);
}
