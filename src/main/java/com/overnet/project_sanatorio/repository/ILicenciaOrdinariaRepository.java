package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.LicenciaOrdinaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILicenciaOrdinariaRepository extends JpaRepository<LicenciaOrdinaria, Integer>{
    
}
