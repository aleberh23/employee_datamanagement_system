package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ISectorRepository extends JpaRepository<Sector, Integer>{
    
}
