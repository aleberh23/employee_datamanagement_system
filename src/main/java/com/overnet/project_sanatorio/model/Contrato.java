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
import java.time.LocalDate;

import java.util.Calendar;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
@Entity(name="contrato")
@Getter
@Setter
public class Contrato implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_contrato")
    private int idContrato;
    @Temporal(TemporalType.DATE)
    @Column(name="fecha_inicio")
    @DateTimeFormat (pattern="yyyy-MM-dd")
    private LocalDate fechaInicio;
    @Temporal(TemporalType.DATE)
    @Column(name="fecha_fin")
    @DateTimeFormat (pattern="yyyy-MM-dd")
    private LocalDate fechaFin;
    @Column(name="descripcion")
    private String descripcion;
    @Column(name="baja")
    private boolean baja;
    @Column(name="estado")
    private String estado;
    @ManyToOne
    @JoinColumn(name="id_empleado")
    private Empleado empleado;
    @ManyToMany(mappedBy="contratos")
    private List<DetalleParteDiario>detallespd;

    public Contrato(int idContrato, LocalDate fechaInicio, LocalDate fechaFin, String descripcion, boolean baja, String estado, Empleado empleado) {
        this.idContrato = idContrato;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.baja = baja;
        this.estado = estado;
        this.empleado = empleado;
        //this.detallespd = new ArrayList();
    }

    public Contrato() {
        //this.detallespd = new ArrayList();
    }
    
}
