package com.overnet.project_sanatorio.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="EMPLEADO")
public class Empleado implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_empleado")
    private int id;
    @Column(name="nombre")
    private String nombre;
    @Column(name="apellido")
    private String apellido;
    @Column(name="correo_electronico")
    private String correoElectronico;
    @Column(name="nro_cel")
    private BigInteger nroCelular;
    @Column(name="tipo_documento")
    private String tipoDocumento;
    @Column(name="nro_documento")
    private BigInteger nroDocumento;
    @Column(name="nro_legajo")
    private BigInteger nroLegajo;
    @Column(name="nro_cuil")
    private BigInteger cuil;
    @Column(name="es_contratado")
    private boolean contratado;
    @Column(name="fecha_ingreso")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate fechaIngreso;
    @Column(name="baja")
    private boolean baja;
    @Column(name="fecha_baja")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate fechaBaja;
    @Column(name="fecha_nacimiento")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate fechaNacimiento;
    @Column(name="fecha_jubilacion")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate fechaJubilacion;
    @ManyToOne
    @JoinColumn(name="id_sector")
    private Sector sector;
    @OneToMany(mappedBy = "empleado")
    private List<Ausencia> ausencias;
    @OneToMany(mappedBy = "empleado")
    private List<Inasistencia> inasistencias;
    @OneToMany(mappedBy = "empleado")
    private List<LicenciaOrdinaria> licenciasOrd;
    @OneToMany(mappedBy = "empleado")
    private List<LicenciaTomada> licenciasTomadas;
    @OneToMany(mappedBy = "empleado")
    private List<Contrato> contratos;
    @OneToMany(mappedBy = "empleado")
    private List<CargaDeFamilia> cargasDeFamilia;
    @OneToMany(mappedBy = "empleado")
    private List<Domicilio>domicilios;
    @ManyToMany(mappedBy="empleados")
    private List<DetalleParteDiario>detallespd;
    
    
      public int calcularDiasDeLicencia() {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaIngreso = this.fechaIngreso; 

        long semanasTrabajo = ChronoUnit.WEEKS.between(fechaIngreso, fechaActual);

        if (semanasTrabajo >= 4 && semanasTrabajo <= 7) {
            return 1;
        } else if (semanasTrabajo >= 8 && semanasTrabajo <= 11) {
            return 2;
        } else if (semanasTrabajo >= 12 && semanasTrabajo <= 15) {
            return 3;
        } else if (semanasTrabajo >= 16 && semanasTrabajo <= 19) {
            return 4;
        } else if (semanasTrabajo > 20 && semanasTrabajo <= 26) { 
            return 5;
        } else if (semanasTrabajo > 26 && semanasTrabajo <= 260) { 
            return 14;
        } else if (semanasTrabajo > 260 && semanasTrabajo <= 520) { 
            return 21;
        } else if (semanasTrabajo > 520 && semanasTrabajo <= 1040) { 
            return 28;
        } else if (semanasTrabajo > 1040) { 
            return 35;
        } else {
            return 0; 
        }
    }
    
    
}
