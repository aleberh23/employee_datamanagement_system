package com.overnet.project_sanatorio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DomicilioReporteDTO {
    private String calle;
    private String nro;
    private String localidad;
    private String auditoria;
    private String activo;
}
