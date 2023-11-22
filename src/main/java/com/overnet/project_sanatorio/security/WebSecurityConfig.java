package com.overnet.project_sanatorio.security;

import com.overnet.project_sanatorio.service.IUsuarioService;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private IUsuarioService usuarioser;
   
    
   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
                httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth
                                 //dar acceso a css (si habria)                                      
                                .requestMatchers("/css/**").permitAll()
                                 //acceso a ficheros javascript (si habria)
                                .requestMatchers("/js/**").permitAll()
                                .requestMatchers("/registro").permitAll()
                                 //acceso a una hipotetica direccion que no requiera autenticacion (ej: /ayuda)
                                 //.requestMatchers("/ayuda").permitAll()
                                .requestMatchers("/empleado/**").hasAnyAuthority("RRHH")
                                .requestMatchers("/domicilio/**").hasAnyAuthority("RRHH")
                                .requestMatchers("/inasistencias/temporal").hasAnyAuthority("M_ENTRADA")
                                .anyRequest().authenticated()
                                )
                               
				//.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				//.authenticationProvider(authenticationProvider())
                        //.httpBasic(withDefaults())
                        .formLogin(form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/loginprocess")
                                .defaultSuccessUrl("/")
                                .permitAll()
                        )
                        
                        .logout(logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                              )
                
                //.logout((logout)->logout.logoutSuccessUrl("/logout").permitAll())
                   
                        .authenticationProvider(authenticationProvider())
                        .userDetailsService(usuarioser);
                        
        
                               
                              
                return httpSecurity.build();
                
    }
  
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
   @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(usuarioser);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

  
    
}
