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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
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
    private Calendar fechaIngreso;
    @Temporal(TemporalType.DATE)
    @Column(name="fecha_baja")
    private Calendar fechaBaja;
    @Temporal(TemporalType.DATE)
    @Column(name="fecha_nacimiento")
    private Calendar fechaNacimiento;
    @Temporal(TemporalType.DATE)
    @Column(name="fecha_jubilacion")
    private Calendar fechaJubilacion;
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
    

    public Empleado(int id, String nombre, String apellido, String correoElectronico, BigInteger nroCelular, String tipoDocumento, BigInteger nroDocumento, BigInteger nroLegajo, BigInteger cuil, boolean contratado, Calendar fechaIngreso, Calendar fechaBaja, Calendar fechaNacimiento, Calendar fechaJubilacion/*, Sector sector*/) {
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
    
    
}
