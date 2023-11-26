package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.CargaDeFamilia;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICargaDeFamiliaRepository extends JpaRepository<CargaDeFamilia, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE carga_de_familia c SET c.baja = true WHERE c.id = ?1")
    public void darDeBaja(int cargaDeFamiliaID);
    @Query("SELECT c FROM carga_de_familia c WHERE c.baja = TRUE")
    public List<CargaDeFamilia> findAllActivas();
}
