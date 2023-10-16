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
    private Calendar fechaInicio;
    @Temporal(TemporalType.DATE)
    @Column(name="fecha_fin")
    private Calendar fechaFin;
    @Column(name="descripcion")
    private String descripcion;
    @ManyToOne
    @JoinColumn(name="id_empleado")
    private Empleado empleado;
    @ManyToMany(mappedBy="contratos")
    private List<DetalleParteDiario>detallespd;

    public Contrato(int idContrato, Calendar fechaInicio, String descripcion, Empleado empleado) {
        this.idContrato = idContrato;
        this.fechaInicio = fechaInicio;
        this.descripcion = descripcion;
        this.empleado = empleado;
        this.detallespd = new ArrayList();
    }

    public Contrato() {
        this.detallespd = new ArrayList();
    }
    
}
