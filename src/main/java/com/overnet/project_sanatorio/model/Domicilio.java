package com.overnet.project_sanatorio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="domicilio")
public class Domicilio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_domicilio")
    private int id;
    @Column(name="calle")
    private String calle;
    @Column(name="numero")
    private int numero;
    @Column(name="localidad")
    private String localidad;
    @Column(name="auditoria_medica")
    private boolean auditoriaMedica;
    @Column(name="baja")
    private boolean baja;
    @ManyToOne
    @JoinColumn(name="id_empleado")
    private Empleado empleado;

    public Domicilio(int id, String calle, int numero, String localidad, boolean auditoriaMedica, boolean baja, Empleado empleado) {
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.auditoriaMedica = auditoriaMedica;
        this.empleado = empleado;
        this.baja=baja;
    }

    public Domicilio() {
    }

}
