package com.dattp.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableInCartResponseDTO {
    private Long id;
    private Long tableId;
    private String name;
    private float price;
    public TableInCartResponseDTO() {
    }
}
