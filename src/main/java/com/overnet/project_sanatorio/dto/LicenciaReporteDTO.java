package com.overnet.project_sanatorio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LicenciaReporteDTO {
    private String nroLegajo;
    private String empleado;
    private String sector;
    private String fechaDesde;
    private String fechaHasta;
    private String tipo;
    private String estado;
    private String certificado;
}
