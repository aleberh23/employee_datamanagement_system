package com.overnet.project_sanatorio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoReporteDTO {
    private String nroLegajo;
    private String nombre;
    private String apellido;
    private String nombreyapellido;
    private String tipoDocumento;
    private String nroDocumento;
    private String sexo;
    private String fechaNacimiento;
    private String fechaBaja;
    private String fechaJubilacion;
    private String nroCuil;
    private String estudiante;
    private String activo;
    private String fechaIngreso;
    private String contratado;
    private String sector;
    private String correoElectronico;
    private String nroTelefono;
}
