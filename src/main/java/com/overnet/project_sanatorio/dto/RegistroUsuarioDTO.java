package com.overnet.project_sanatorio.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroUsuarioDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;

    public RegistroUsuarioDTO() {
    }

    public RegistroUsuarioDTO(Long id, String nombre, String apellido, String email, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
    }

    public RegistroUsuarioDTO(String nombre, String apellido, String email, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
    }

    public RegistroUsuarioDTO(String email) {
        this.email = email;
    }
    
     
    
}
