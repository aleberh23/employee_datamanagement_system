package com.overnet.project_sanatorio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InasistenciaReporteDTO {
    private String nroLegajo;
    private String empleado;
    private String sector;
    private String fecha;
    private String motivo;
    private String descripcion;
    private String certificado;
}
