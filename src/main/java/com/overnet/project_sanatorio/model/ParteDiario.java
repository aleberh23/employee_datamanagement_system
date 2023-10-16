package com.overnet.project_sanatorio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Entity(name="parte_diario")
public class ParteDiario implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_pdiario")
    private int id;
    @Temporal(TemporalType.DATE)
    @Column(name="fecha_emision")
    private Calendar fechaEmision;
    @OneToMany(mappedBy="parteDiario")
    private List<DetalleParteDiario> detallespd;

    public ParteDiario(int id, Calendar fechaEmision) {
        this.id = id;
        this.fechaEmision = fechaEmision;
        this.detallespd=new ArrayList();
    }

    public ParteDiario() {
       this.detallespd=new ArrayList();
    }
    
}
