package com.dattp.order.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeriodsTimeBookedTableDTO {
    private Long id;
    private String name;
    private List<PeriodTimeResponseDTO> times;
    public PeriodsTimeBookedTableDTO(Long id, String name, List<PeriodTimeResponseDTO> times) {
        this.id = id;
        this.name = name;
        this.times = times;
    }
    public PeriodsTimeBookedTableDTO() {
    }
    @Override
    public boolean equals(Object obj) {
        return this.id.equals((Long)obj);
    } 
}