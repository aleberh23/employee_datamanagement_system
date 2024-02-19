package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.Sexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISexoRepository extends JpaRepository<Sexo, Integer>{
    
}
