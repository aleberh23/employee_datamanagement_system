package com.overnet.project_sanatorio.dto;

import com.overnet.project_sanatorio.model.Sector;
import com.overnet.project_sanatorio.model.Sexo;
import java.math.BigInteger;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoRegistroDTO {
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private BigInteger nroCelular;
    private String tipoDocumento;
    private BigInteger nroDocumento;
    private BigInteger nroLegajo;
    private BigInteger cuil;
    private boolean contratado;
    private LocalDate fechaIngreso;
    private boolean baja;
    private boolean estudiante;
    private LocalDate fechaBaja;
    private LocalDate fechaNacimiento;
    private Sector sector;
    private Sexo sexo;
    //domicilio
    private String calle;
    private Integer numero;
    private String localidad;
    private boolean auditoriaMedica;
}
