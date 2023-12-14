package com.overnet.project_sanatorio.dto;

import java.math.BigInteger;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class LicenciaOrdinariaDTO {
    private int id;
    private int idEmp;
    BigInteger nroLegajo;
    private String nombreEmp;
    private String apellidoEmp;
    private int anio;
    private int cantDias;
    
}
