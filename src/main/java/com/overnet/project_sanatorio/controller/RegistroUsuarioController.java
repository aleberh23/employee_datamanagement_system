package com.overnet.project_sanatorio.controller;

import com.overnet.project_sanatorio.dto.RegistroUsuarioDTO;
import com.overnet.project_sanatorio.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registro")
public class RegistroUsuarioController {
    @Autowired
    private UsuarioService usuarioser;
    
    @ModelAttribute("usuario")
    public RegistroUsuarioDTO retornarNuevoUsrRegistroDTO(){
        return new RegistroUsuarioDTO();
    }
    
    @GetMapping
    public String mostrarFormRegistro(){
        //si es necesario hacer la plantilla de registro, la logica ya esta aca
        return "registro";
    }
    
    @PostMapping
    public String registrarUsuario(@ModelAttribute("usuario") RegistroUsuarioDTO usrReg){
        usuarioser.save(usrReg);
        return "redirect:/registro?exito";
    }
}
