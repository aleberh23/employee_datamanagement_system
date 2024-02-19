package com.overnet.project_sanatorio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContratoReporteDTO {
    private String nroLegajo;
    private String empleado;
    private String sector;
    private String fechaInicio;
    private String fechaFin;
    private String estado;
    private String descripcion;
}
