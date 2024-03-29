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
@Entity(name="contrato")
public class Contrato implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_contrato")
    private int idContrato;
    @Column(name="fecha_inicio")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate fechaInicio;
    @Column(name="fecha_fin")
    @DateTimeFormat(iso = ISO.DATE)
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
    
}
