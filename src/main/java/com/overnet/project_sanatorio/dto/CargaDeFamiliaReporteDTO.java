package com.overnet.project_sanatorio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CargaDeFamiliaReporteDTO {
    private String nombre;
    private String vinculo;
    private String tipoDoc;
    private String nroDoc;
    private String activa;
}
