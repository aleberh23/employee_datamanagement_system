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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
@Getter
@Setter
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
    @Temporal(TemporalType.DATE)
    @Column(name="fecha_ingreso")
    @DateTimeFormat (pattern="yyyy-MM-dd")
    private LocalDate fechaIngreso;
    @Column(name="baja")
    private boolean baja;
    @Temporal(TemporalType.DATE)
    @Column(name="fecha_baja")
    @DateTimeFormat (pattern="yyyy-MM-dd")
    private LocalDate fechaBaja;
    @Temporal(TemporalType.DATE)
    @Column(name="fecha_nacimiento")
    @DateTimeFormat (pattern="yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    @Temporal(TemporalType.DATE)
    @Column(name="fecha_jubilacion")
    @DateTimeFormat (pattern="yyyy-MM-dd")
    private LocalDate fechaJubilacion;
    @ManyToOne
    @JoinColumn(name="id_sector")
    private Sector sector;
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
    

    public Empleado(int id, String nombre, String apellido, String correoElectronico, BigInteger nroCelular, String tipoDocumento, BigInteger nroDocumento, BigInteger nroLegajo, BigInteger cuil, boolean contratado, LocalDate fechaIngreso, LocalDate fechaBaja, LocalDate fechaNacimiento, LocalDate fechaJubilacion, Sector sector) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.nroCelular = nroCelular;
        this.tipoDocumento = tipoDocumento;
        this.nroDocumento = nroDocumento;
        this.nroLegajo = nroLegajo;
        this.cuil = cuil;
        this.contratado = contratado;
        this.fechaIngreso = fechaIngreso;
        this.fechaBaja = fechaBaja;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaJubilacion = fechaJubilacion;
        this.sector=sector;
        this.licenciasOrd=new ArrayList();
        this.licenciasTomadas=new ArrayList();
        this.contratos=new ArrayList();
        this.cargasDeFamilia=new ArrayList();
        this.detallespd=new ArrayList();
    }
  

    public Empleado() {
       this.licenciasOrd=new ArrayList();
       this.licenciasTomadas=new ArrayList();
       this.contratos=new ArrayList();
       this.cargasDeFamilia=new ArrayList();
       this.detallespd=new ArrayList();
    }
    
      public int calcularDiasDeLicencia() {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaIngreso = this.fechaIngreso; // Suponiendo que tienes un campo fechaIngreso en tu clase Empleado

        long semanasTrabajo = ChronoUnit.WEEKS.between(fechaIngreso, fechaActual);

        if (semanasTrabajo >= 4 && semanasTrabajo <= 7) {
            return 1;
        } else if (semanasTrabajo >= 8 && semanasTrabajo <= 11) {
            return 2;
        } else if (semanasTrabajo >= 12 && semanasTrabajo <= 15) {
            return 3;
        } else if (semanasTrabajo >= 16 && semanasTrabajo <= 19) {
            return 4;
        } else if (semanasTrabajo > 20 && semanasTrabajo <= 26) { // 26 semanas es aproximadamente 6 meses
            return 5;
        } else if (semanasTrabajo > 26 && semanasTrabajo <= 260) { // 260 semanas es aproximadamente 5 años
            return 14;
        } else if (semanasTrabajo > 260 && semanasTrabajo <= 520) { // 520 semanas es aproximadamente 10 años
            return 21;
        } else if (semanasTrabajo > 520 && semanasTrabajo <= 1040) { // 1040 semanas es aproximadamente 20 años
            return 28;
        } else if (semanasTrabajo > 1040) { // Más de 20 años
            return 35;
        } else {
            return 0; // En caso de que no se cumpla ninguna condición
        }
    }
    
    
}
