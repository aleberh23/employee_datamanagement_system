package com.overnet.project_sanatorio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity(name="licencia_tomada")
public class LicenciaTomada implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_licencia_tomada")
    private int idLicenciaTomada;
    @Temporal(TemporalType.DATE)
    @Column(name="fecha_desde")
    private Calendar fechaDesde;
    @Temporal(TemporalType.DATE)
    @Column(name="fecha_hasta")
    private Calendar fechaHasta;
    @Column(name="tipo_licencia")
    private String tipo;
    @Column(name="descripcion")
    private String descripcion;
    @Column(name="certificado")
    private boolean certificado;
    @ManyToOne
    @JoinColumn(name="id_empleado")
    private Empleado empleado;
    /*@ManyToMany(mappedBy="licTomadas")
    private List<DetalleParteDiario>detallespd;*/

    public LicenciaTomada(int idLicenciaTomada, Calendar fechaDesde, Calendar fechaHasta, String tipo, String descripcion, boolean certificado, Empleado empleado) {
        this.idLicenciaTomada = idLicenciaTomada;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.certificado = certificado;
        this.empleado = empleado;
        //this.detallespd=new ArrayList();
    }

    public LicenciaTomada() {
        //this.detallespd=new ArrayList();
    }

    
}
