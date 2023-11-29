package com.overnet.project_sanatorio.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class LicenciaOrdinariaDTO {
    private int id;
    private int idEmp;
    private String nombreEmp;
    private String apellidoEmp;
    private int anio;
    private int cantDias;
    
}
