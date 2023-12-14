package com.overnet.project_sanatorio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ausencia")
public class Ausencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fecha;
    @ManyToOne
    @JoinColumn(name="id_tipo_inasistencia")
    private TipoInasistencia tipoInasistencia;
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;
}
