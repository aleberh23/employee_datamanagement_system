package com.overnet.project_sanatorio.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TipoLicenciaDTO {
    private int idTipo;
    private String nombre;
    private int diasTomados;
    private Integer diasTotales;
    private Integer diasRestantes;
}
