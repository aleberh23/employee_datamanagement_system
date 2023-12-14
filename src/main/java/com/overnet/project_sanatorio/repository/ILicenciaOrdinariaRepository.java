package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.LicenciaOrdinaria;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ILicenciaOrdinariaRepository extends JpaRepository<LicenciaOrdinaria, Integer>{
    @Query("SELECT l FROM licencia_ordinaria l WHERE l.empleado.id = ?1 AND l.anio = ?2")
    public LicenciaOrdinaria findByEmpleadoAndAnio(int idEmpleado, int anio);
    @Transactional
    @Modifying  
    @Query("DELETE FROM licencia_ordinaria l WHERE l.anio = ?1") 
    public void deleteByAnio(int anio);
    @Transactional
    @Modifying
    @Query("DELETE FROM licencia_ordinaria")
    void deleteAllLicenciasOrdinarias();

    
}
