package com.overnet.project_sanatorio.dto;

import java.time.LocalDate;
import java.time.Month;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PeriodoDTO {
    private int anio;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    
    public PeriodoDTO(int anio) {
        this.anio = anio;
        this.fechaDesde=LocalDate.of(anio, Month.OCTOBER, 1);
        this.fechaHasta=LocalDate.of(anio+1, Month.SEPTEMBER, 30);
    }
    
    
}
