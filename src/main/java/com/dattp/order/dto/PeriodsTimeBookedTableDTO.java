package com.dattp.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    return this.id.equals(obj);
  }
}