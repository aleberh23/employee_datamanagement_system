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
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="detalle_pdiario")
public class DetalleParteDiario implements Serializable {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name="detalle_parte_diario")
    private int idDetallePd;
    @ManyToOne
    @JoinColumn(name="id_sectorpdiario")
    private SectorDetalleParteDiario sector;
    @ManyToOne
    @JoinColumn(name="id_pdiario")
    private ParteDiario parteDiario;
    @ManyToMany
    @JoinTable(name="empleado_detallepdiario",
            joinColumns =@JoinColumn(name="id_detallepd"),
            inverseJoinColumns=@JoinColumn(name="id_empleado"))
    private List<Empleado> empleados;
    @ManyToMany
    @JoinTable(name="contrato_detallepdiario",
            joinColumns =@JoinColumn(name="id_detallepd"),
            inverseJoinColumns=@JoinColumn(name="id_contrato"))
    private List<Contrato> contratos;
    @ManyToMany
    @JoinTable(name="licTomada_detallepdiario",
            joinColumns =@JoinColumn(name="id_detallepd"),
            inverseJoinColumns=@JoinColumn(name="id_licencia_tomada"))
    private List<LicenciaTomada> licTomadas;
    
    @ManyToMany
    @JoinTable(name="inasistencia_detallepdiario",
            joinColumns =@JoinColumn(name="id_detallepd"),
            inverseJoinColumns=@JoinColumn(name="id"))
    private List<Inasistencia> inasistencias;
}
