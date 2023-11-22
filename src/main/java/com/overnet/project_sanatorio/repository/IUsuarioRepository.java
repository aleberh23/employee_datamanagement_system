package com.overnet.project_sanatorio.repository;

import com.overnet.project_sanatorio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long>{
    public Usuario findByEmail(String email);
}
