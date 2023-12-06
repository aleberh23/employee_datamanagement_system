package com.overnet.project_sanatorio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
@Getter
@Setter
@Entity(name="licencia_tomada")
public class LicenciaTomada implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_licencia_tomada")
    private int idLicenciaTomada;
    @Column(name="fecha_desde")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate fechaDesde;
    @Column(name="fecha_hasta")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate fechaHasta;
    @Column(name="tipo_licencia")
    private String tipo;
    @Column(name="descripcion")
    private String descripcion;
    @Column(name="certificado")
    private boolean certificado;
    @Column(name="terminada")
    private boolean terminada;
    @ManyToOne
    @JoinColumn(name="id_empleado")
    private Empleado empleado;
    @ManyToOne
    @JoinColumn(name="id_tipo")
    private TipoLicencia tipoLicencia;
    @ManyToMany(mappedBy="licTomadas")
    private List<DetalleParteDiario>detallespd;

    public LicenciaTomada(int idLicenciaTomada, LocalDate fechaDesde, LocalDate fechaHasta, String tipo, String descripcion, boolean certificado, boolean terminada, Empleado empleado, TipoLicencia tipoLicencia) {
        this.idLicenciaTomada = idLicenciaTomada;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.certificado = certificado;
        this.terminada = terminada;
        this.empleado = empleado;
        this.tipoLicencia = tipoLicencia;
        this.detallespd=new ArrayList();
    }

    public LicenciaTomada() {
        this.detallespd=new ArrayList();
    }

    
}
