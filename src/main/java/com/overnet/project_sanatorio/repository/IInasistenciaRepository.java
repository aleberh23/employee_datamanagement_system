package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.Inasistencia;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IInasistenciaRepository extends JpaRepository<Inasistencia, Integer>{
    @Query("SELECT i FROM Inasistencia i WHERE i.fecha = ?1")
    public List<Inasistencia> findByFecha(LocalDate fecha);
}
