package com.overnet.project_sanatorio.service;

import com.overnet.project_sanatorio.dto.RegistroUsuarioDTO;
import com.overnet.project_sanatorio.model.Rol;
import com.overnet.project_sanatorio.model.Usuario;
import com.overnet.project_sanatorio.repository.IUsuarioRepository;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
    public class UsuarioService implements IUsuarioService{
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private IUsuarioRepository usuariorep;
    
    
    @Autowired
    public UsuarioService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    
   @Override
    public Usuario save(RegistroUsuarioDTO registroDTO) {
        Usuario usr = new Usuario(registroDTO.getNombre(), registroDTO.getApellido(), 
                registroDTO.getEmail(), passwordEncoder.encode(registroDTO.getPassword()));
      return usuariorep.save(usr);
    }

   @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario user = usuariorep.findByEmail(email);
          if(user==null){
                throw new UsernameNotFoundException("User not exists by Username");
               }
        System.out.println(user.getEmail()+" "+user.getPassword());
        
        user.getRoles().forEach(r->System.out.println("ROL: "+r.getNombre()));
        Collection<? extends GrantedAuthority>authorities=mapearAutoridadesARoles(user.getRoles());
        authorities.forEach(a->System.out.println("AUT."+a.getAuthority()));
        return new User(user.getEmail(), user.getPassword(), authorities) {
            @Override
            public String getUsername() {
                return user.getNombre()+" "+user.getApellido(); // Se establece el nombre del usuario como el nombre de usuario en Thymeleaf
            }
        };
    }
    private Collection<? extends GrantedAuthority> mapearAutoridadesARoles(Collection<Rol>roles){
        return roles.stream().map(role->new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    }

}
