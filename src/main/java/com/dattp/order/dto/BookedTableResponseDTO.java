package com.dattp.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookedTableResponseDTO {
    private long id;
    private int state;
    private long tableId;
    private float price;
    private String name;
    public BookedTableResponseDTO(long id, long tableId, String name, float price) {
        this.id = id;
        this.tableId = tableId;
        this.name = name;
        this.price = price;
    }
    public BookedTableResponseDTO() {
    }
    @Override
    public boolean equals(Object obj) {
        BookedTableResponseDTO other = (BookedTableResponseDTO) obj;
        return this.id == other.id;
    }
}
