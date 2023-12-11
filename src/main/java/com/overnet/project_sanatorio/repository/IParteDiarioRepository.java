package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.ParteDiario;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IParteDiarioRepository extends JpaRepository<ParteDiario, Integer>{
    @Query("SELECT p FROM parte_diario p WHERE p.fechaEmision = ?1")
    public ParteDiario findByFecha(LocalDate fecha);
}
