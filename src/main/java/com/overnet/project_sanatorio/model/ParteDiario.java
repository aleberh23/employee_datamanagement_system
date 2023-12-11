package com.overnet.project_sanatorio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="parte_diario")
public class ParteDiario implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_pdiario")
    private int id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="fecha_emision")
    private LocalDate fechaEmision;
    @OneToMany(mappedBy="parteDiario")
    private List<DetalleParteDiario> detallespd;
    
}
