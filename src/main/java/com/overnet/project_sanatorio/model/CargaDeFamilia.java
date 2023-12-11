package com.overnet.project_sanatorio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="carga_de_familia")
public class CargaDeFamilia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_carga_familia")
    private int id;
    @Column(name="tipo_documento")
    private String tipoDoc;
    @Column(name="nro_documento")
    private int nroDoc;
    @Column(name="nombre")
    private String nombre;
    @Column(name="apellido")
    private String apellido;
    @Column(name="vinculo")
    private String vinculo;
    @Column(name="baja")
    private boolean baja;
    @ManyToOne
    @JoinColumn(name="id_empleado")
    private Empleado empleado;

}
