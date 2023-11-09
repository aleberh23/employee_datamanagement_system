package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.Domicilio;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IDomicilioRepository extends JpaRepository<Domicilio, Integer>{
    @Modifying
    @Transactional
    @Query("UPDATE domicilio d SET d.baja = true WHERE d.id = ?1")
    public void darDeBaja(int domicilioId);
    @Query("SELECT d FROM domicilio d WHERE d.baja = TRUE")
    public List<Domicilio> findAllActivos();
}
