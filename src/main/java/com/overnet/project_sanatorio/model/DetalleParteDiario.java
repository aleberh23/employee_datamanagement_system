package com.overnet.project_sanatorio.model;

import com.overnet.project_sanatorio.model.ParteDiario;
import com.overnet.project_sanatorio.model.LicenciaTomada;
import com.overnet.project_sanatorio.model.Contrato;
import com.overnet.project_sanatorio.model.Empleado;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity(name="detalle_pdiario")
public class DetalleParteDiario implements Serializable {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name="detalle_parte_diario")
    private int idDetallePd;
    @Column(name="sector")
    private int sector;
    @ManyToOne
    @JoinColumn(name="id_pdiario")
    private ParteDiario parteDiario;
    @ManyToMany
    @JoinTable(name="empleado_detallepdiario",
            joinColumns =@JoinColumn(name="id_detallepd"),
            inverseJoinColumns=@JoinColumn(name="id_empleado"))
    private List<Empleado> empleados;
    @JoinTable(name="contrato_detallepdiario",
            joinColumns =@JoinColumn(name="id_detallepd"),
            inverseJoinColumns=@JoinColumn(name="id_contrato"))
    private List<Contrato> contratos;
    /*@JoinTable(name="licTomada_detallepdiario",
            joinColumns =@JoinColumn(name="id_detallepd"),
            inverseJoinColumns=@JoinColumn(name="id_licencia_tomada"))
    private List<LicenciaTomada> licTomadas;*/

    public DetalleParteDiario(int idDetallePd, int sector, ParteDiario parteDiario) {
        this.idDetallePd = idDetallePd;
        this.sector = sector;
        this.parteDiario = parteDiario;
        this.empleados = new ArrayList();
        this.contratos = new ArrayList();
        //this.licTomadas = new ArrayList();
    }

    public DetalleParteDiario() {
        this.empleados = new ArrayList();
        this.contratos = new ArrayList();
       // this.licTomadas = new ArrayList();
    }
}
