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
@Entity(name = "licencia_ordinaria")
public class LicenciaOrdinaria implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_licencia_ordinaria")
    private int idLicenciaOrdinaria;
    @Column(name="anio")
    private int anio;
    @Column(name="cantidad_dias")
    private int cantidadDeDias;
    @ManyToOne
    @JoinColumn(name="id_empleado")
    private Empleado empleado;

    public LicenciaOrdinaria(int idLicenciaOrdinaria, int anio, int cantidadDeDias, Empleado empleado) {
        this.idLicenciaOrdinaria = idLicenciaOrdinaria;
        this.anio = anio;
        this.cantidadDeDias = cantidadDeDias;
        this.empleado = empleado;
    }

    public LicenciaOrdinaria() {
    }

}

                 