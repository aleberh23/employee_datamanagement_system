package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.dto.RegistroUsuarioDTO;
import com.overnet.project_sanatorio.model.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUsuarioService extends UserDetailsService {
    
    public Usuario save(RegistroUsuarioDTO registroDTO);
}
